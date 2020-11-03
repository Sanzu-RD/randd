package com.gempukku.libgdx.graph.shader;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLTexture;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.FlushablePool;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.ObjectMap;
import com.gempukku.libgdx.graph.shader.models.impl.GraphShaderModelInstance;

import static com.badlogic.gdx.graphics.GL20.GL_BACK;
import static com.badlogic.gdx.graphics.GL20.GL_FRONT;
import static com.badlogic.gdx.graphics.GL20.GL_NONE;

public abstract class BasicShader implements UniformRegistry, Disposable {
    public enum Culling {
        back(GL_BACK), none(GL_NONE), front(GL_FRONT);

        private int cullFace;

        Culling(int cullFace) {
            this.cullFace = cullFace;
        }

        public void setCullFace(RenderContext renderContext) {
            renderContext.setCullFace(cullFace);
        }
    }

    public enum Transparency {
        opaque(true), transparent(false);

        private boolean depthMask;

        Transparency(boolean depthMask) {
            this.depthMask = depthMask;
        }

        void setDepthMask(RenderContext renderContext) {
            renderContext.setDepthMask(depthMask);
        }
    }

    public enum Blending {
        alpha(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA),
        additive(GL20.GL_SRC_ALPHA, GL20.GL_ONE),
        none(GL20.GL_ZERO, GL20.GL_ONE);

        private int sourceFactor;
        private int destinationFactor;

        Blending(int sourceFactor, int destinationFactor) {
            this.sourceFactor = sourceFactor;
            this.destinationFactor = destinationFactor;
        }

        public int getSourceFactor() {
            return sourceFactor;
        }

        public int getDestinationFactor() {
            return destinationFactor;
        }
    }

    public enum DepthTesting {
        less(GL20.GL_LESS), less_or_equal(GL20.GL_LEQUAL),
        equal(GL20.GL_EQUAL), not_equal(GL20.GL_NOTEQUAL), greater_or_equal(GL20.GL_GEQUAL),
        greater(GL20.GL_GREATER), never(GL20.GL_NEVER), always(GL20.GL_ALWAYS),
        disabled(0);

        private int depthFunction;

        DepthTesting(int depthFunction) {
            this.depthFunction = depthFunction;
        }

        void setDepthTest(RenderContext renderContext, float depthNear, float depthFar) {
            renderContext.setDepthTest(depthFunction, depthNear, depthFar);
        }
    }

    private static class Attribute {
        private final String alias;
        private int location = -1;

        public Attribute(String alias) {
            this.alias = alias;
        }

        private void setLocation(int location) {
            this.location = location;
        }
    }

    private static class Uniform {
        private final String alias;
        private final boolean global;
        private final UniformSetter setter;
        private int location = -1;

        public Uniform(String alias, boolean global, UniformSetter setter) {
            this.alias = alias;
            this.global = global;
            this.setter = setter;
        }

        private void setUniformLocation(int location) {
            this.location = location;
        }
    }

    private static class StructArrayUniform {
        private final String alias;
        private final String[] fieldNames;
        private final boolean global;
        private final StructArrayUniformSetter setter;
        private int startIndex;
        private int size;
        private int[] fieldOffsets;

        public StructArrayUniform(String alias, String[] fieldNames, boolean global, StructArrayUniformSetter setter) {
            this.alias = alias;
            this.fieldNames = new String[fieldNames.length];
            System.arraycopy(fieldNames, 0, this.fieldNames, 0, fieldNames.length);
            this.global = global;
            this.setter = setter;
        }

        private void setUniformLocations(int startIndex, int size, int[] fieldOffsets) {
            this.startIndex = startIndex;
            this.size = size;
            this.fieldOffsets = fieldOffsets;
        }
    }

    private final ObjectMap<String, Attribute> attributes = new ObjectMap<>();
    private final ObjectMap<String, Uniform> uniforms = new ObjectMap<String, Uniform>();
    private final ObjectMap<String, StructArrayUniform> structArrayUniforms = new ObjectMap<String, StructArrayUniform>();

    private ShaderProgram program;
    private RenderContext context;
    private Texture defaultTexture;
    private Mesh currentMesh;
    private Culling culling = Culling.back;
    private Transparency transparency = Transparency.opaque;
    private Blending blending = Blending.alpha;
    private DepthTesting depthTesting = DepthTesting.less;

    private boolean usingDepthTexture;
    private boolean usingColorTexture;

    private boolean initialized = false;
    protected final RenderablePool renderablesPool = new RenderablePool();
    /**
     * list of Renderables to be rendered in the current batch
     **/
    protected final Array<Renderable> renderables = new Array<Renderable>();

    public BasicShader(Texture defaultTexture) {
        this.defaultTexture = defaultTexture;
    }

    public Texture getDefaultTexture() {
        return defaultTexture;
    }

    public boolean isUsingDepthTexture() {
        return usingDepthTexture;
    }

    public void setUsingDepthTexture(boolean usingDepthTexture) {
        this.usingDepthTexture = usingDepthTexture;
    }

    public boolean isUsingColorTexture() {
        return usingColorTexture;
    }

    public void setUsingColorTexture(boolean usingColorTexture) {
        this.usingColorTexture = usingColorTexture;
    }

    @Override
    public void registerAttribute(String alias) {
        if (initialized) throw new GdxRuntimeException("Cannot register an uniform after initialization");
        validateNewAttribute(alias);
        attributes.put(alias, new Attribute(alias));
    }

    @Override
    public void registerUniform(final String alias, final boolean global, final UniformSetter setter) {
        if (initialized) throw new GdxRuntimeException("Cannot register an uniform after initialization");
        validateNewUniform(alias, global, setter);
        uniforms.put(alias, new Uniform(alias, global, setter));
    }

    @Override
    public void registerStructArrayUniform(final String alias, String[] fieldNames, final boolean global, StructArrayUniformSetter setter) {
        if (initialized) throw new GdxRuntimeException("Cannot register an uniform after initialization");
        validateNewStructArrayUniform(alias, global, setter);
        structArrayUniforms.put(alias, new StructArrayUniform(alias, fieldNames, global, setter));
    }

    public RenderContext getContext() {
        return context;
    }

    private void validateNewAttribute(String alias) {
        if (attributes.containsKey(alias))
            throw new GdxRuntimeException("Attribute already registered");
    }

    private void validateNewStructArrayUniform(String alias, boolean global, StructArrayUniformSetter setter) {
        Uniform uniform = uniforms.get(alias);
        if (uniform != null &&
                (uniform.global != global || uniform.setter != setter))
            throw new IllegalStateException("Already contains uniform of that name with a different global flag, or setter");
    }

    private void validateNewUniform(String alias, boolean global, UniformSetter setter) {
        Uniform uniform = uniforms.get(alias);
        if (uniform != null &&
                (uniform.global != global || uniform.setter != setter))
            throw new IllegalStateException("Already contains uniform of that name with a different global flag, or setter");
    }

    /**
     * Initialize this shader, causing all registered uniforms/attributes to be fetched.
     *
     * @param program ShaderProgram to initialize
     */
    protected void init(final ShaderProgram program) {
        if (initialized) throw new GdxRuntimeException("Already initialized");
        if (!program.isCompiled()) throw new GdxRuntimeException(program.getLog());
        this.program = program;

        for (Attribute attribute : attributes.values()) {
            final int location = program.getAttributeLocation(attribute.alias);
            if (location >= 0)
                attribute.setLocation(location);
        }

        for (Uniform uniform : uniforms.values()) {
            String alias = uniform.alias;
            int location = getUniformLocation(program, alias);
            uniform.setUniformLocation(location);
        }

        for (StructArrayUniform uniform : structArrayUniforms.values()) {
            int startIndex = getUniformLocation(program, uniform.alias + "[0]." + uniform.fieldNames[0]);
            int size = program.fetchUniformLocation(uniform.alias + "[1]." + uniform.fieldNames[0], false) - startIndex;
            int[] fieldOffsets = new int[uniform.fieldNames.length];
            // Starting at 1, as first field offset is 0 by default
            for (int i = 1; i < uniform.fieldNames.length; i++) {
                fieldOffsets[i] = getUniformLocation(program, uniform.alias + "[0]." + uniform.fieldNames[i]) - startIndex;
            }
            uniform.setUniformLocations(startIndex, size, fieldOffsets);
        }
        initialized = true;
    }

    private int getUniformLocation(ShaderProgram program, String alias) {
        return program.fetchUniformLocation(alias, false);
    }

    private final IntArray tempArray = new IntArray();

    private final int[] getAttributeLocations(final VertexAttributes attrs) {
        tempArray.clear();
        final int n = attrs.size();
        for (int i = 0; i < n; i++) {
            Attribute attribute = attributes.get(attrs.get(i).alias);
            if (attribute != null)
                tempArray.add(attribute.location);
            else
                tempArray.add(-1);
        }
        return tempArray.items;
    }

    public void setCulling(Culling culling) {
        this.culling = culling;
    }

    public void setTransparency(Transparency transparency) {
        this.transparency = transparency;
    }

    public Transparency getTransparency() {
        return transparency;
    }

    public void setBlending(Blending blending) {
        this.blending = blending;
    }

    public void setDepthTesting(DepthTesting depthTesting) {
        this.depthTesting = depthTesting;
    }

    public void begin(ShaderContext shaderContext, RenderContext context) {
        this.context = context;
        program.begin();

        // Set depth mask/testing

        Camera camera = shaderContext.getCamera();

        transparency.setDepthMask(context);
        depthTesting.setDepthTest(context, camera.near, camera.far);
        culling.setCullFace(context);
        setBlending(context, transparency, blending);

        for (Uniform uniform : uniforms.values()) {
            if (uniform.global && uniform.location != -1)
                uniform.setter.set(this, uniform.location, shaderContext, null, null);
        }
        for (StructArrayUniform uniform : structArrayUniforms.values()) {
            if (uniform.global && uniform.startIndex != -1)
                uniform.setter.set(this, uniform.startIndex, uniform.fieldOffsets, uniform.size, shaderContext, null, null);
        }
    }

    private static void setBlending(RenderContext context, Transparency transparency, Blending blending) {
        boolean enabled = transparency == Transparency.transparent;
        context.setBlending(enabled, blending.getSourceFactor(), blending.getDestinationFactor());
    }

    public void render(ShaderContext shaderContext, GraphShaderModelInstance graphShaderModelInstance) {
        graphShaderModelInstance.getRenderables(renderables, renderablesPool);
        for (Renderable renderable : renderables) {
            for (Uniform uniform : uniforms.values()) {
                if (!uniform.global)
                    uniform.setter.set(this, uniform.location, shaderContext, graphShaderModelInstance, renderable);
            }
            for (StructArrayUniform uniform : structArrayUniforms.values()) {
                if (!uniform.global)
                    uniform.setter.set(this, uniform.startIndex, uniform.fieldOffsets, uniform.size, shaderContext, graphShaderModelInstance, renderable);
            }
            MeshPart meshPart = renderable.meshPart;
            Mesh mesh = meshPart.mesh;
            if (currentMesh != mesh) {
                if (currentMesh != null) currentMesh.unbind(program, tempArray.items);
                currentMesh = mesh;
                currentMesh.bind(program, getAttributeLocations(mesh.getVertexAttributes()));
            }
            meshPart.render(program, false);
        }
        renderables.clear();
    }

    public void end() {
        if (currentMesh != null) {
            currentMesh.unbind(program, tempArray.items);
            currentMesh = null;
        }
        program.end();
    }

    @Override
    public void dispose() {
        program = null;
        uniforms.clear();
        structArrayUniforms.clear();
        attributes.clear();
    }

    public void setUniform(final int location, final Matrix4 value) {
        program.setUniformMatrix(location, value);
    }

    public void setUniform(final int location, final Matrix3 value) {
        program.setUniformMatrix(location, value);
    }

    public void setUniform(final int location, final Vector3 value) {
        program.setUniformf(location, value);
    }

    public void setUniform(final int location, final Vector2 value) {
        program.setUniformf(location, value);
    }

    public void setUniform(final int location, final Color value) {
        program.setUniformf(location, value);
    }

    public void setUniform(final int location, final float value) {
        program.setUniformf(location, value);
    }

    public void setUniform(final int location, final float v1, final float v2) {
        program.setUniformf(location, v1, v2);
    }

    public void setUniform(final int location, final float v1, final float v2, final float v3) {
        program.setUniformf(location, v1, v2, v3);
    }

    public void setUniform(final int location, final float v1, final float v2, final float v3, final float v4) {
        program.setUniformf(location, v1, v2, v3, v4);
    }

    public void setUniform(final int location, final int value) {
        program.setUniformi(location, value);
    }

    public void setUniform(final int location, final int v1, final int v2) {
        program.setUniformi(location, v1, v2);
    }

    public void setUniform(final int location, final int v1, final int v2, final int v3) {
        program.setUniformi(location, v1, v2, v3);
    }

    public void setUniform(final int location, final int v1, final int v2, final int v3, final int v4) {
        program.setUniformi(location, v1, v2, v3, v4);
    }

    public void setUniform(final int location, final TextureDescriptor textureDesc) {
        program.setUniformi(location, context.textureBinder.bind(textureDesc));
    }

    public void setUniform(final int location, final GLTexture texture) {
        program.setUniformi(location, context.textureBinder.bind(texture));
    }

    public void setUniformMatrixArray(final int location, float[] values) {
        program.setUniformMatrix4fv(location, values, 0, values.length);
    }

    public void setUniformArray(final int location, float[] values) {
        program.setUniform3fv(location, values, 0, values.length);
    }

    protected static class RenderablePool extends FlushablePool<Renderable> {
        @Override
        protected Renderable newObject() {
            return new Renderable();
        }

        @Override
        public Renderable obtain() {
            Renderable renderable = super.obtain();
            renderable.environment = null;
            renderable.material = null;
            renderable.meshPart.set("", null, 0, 0, 0);
            renderable.shader = null;
            renderable.userData = null;
            return renderable;
        }
    }
}
