package com.gempukku.libgdx.graph.shader;

import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.gempukku.libgdx.graph.shader.node.EndGraphShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.GraphShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.PropertyShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.attribute.AttributeNormalShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.attribute.AttributePositionShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.attribute.AttributeTangentShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.attribute.AttributeUVShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.effect.FresnelEffectShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.effect.IntensityShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.lighting.AmbientLightShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.lighting.ApplyNormalMapShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.lighting.CalculateLightingShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.lighting.DirectionalLightShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.lighting.PointLightShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.lighting.SpotLightShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.material.ColorAttributeShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.material.FloatAttributeShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.material.TextureAttributeShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.arithmetic.AddShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.arithmetic.DivideShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.arithmetic.MultiplyShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.arithmetic.SubtractShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.common.AbsShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.common.CeilingShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.common.ClampShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.common.FloorShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.common.FractionalPartShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.common.LerpShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.common.MaximumShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.common.MinimumShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.common.ModuloShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.common.SaturateShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.common.SignShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.common.SmoothstepShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.common.StepShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.exponential.ExponentialBase2ShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.exponential.ExponentialShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.exponential.InverseSquareRootShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.exponential.LogarithmBase2ShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.exponential.NaturalLogarithmShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.exponential.PowerShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.exponential.SquareRootShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.geometric.CrossProductShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.geometric.DistanceShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.geometric.DotProductShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.geometric.LengthShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.geometric.NormalizeShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.trigonometry.ArccosShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.trigonometry.ArcsinShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.trigonometry.ArctanShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.trigonometry.CosShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.trigonometry.DegreesShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.trigonometry.RadiansShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.trigonometry.SinShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.trigonometry.TanShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.utility.DistanceFromPlaneShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.value.MergeShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.value.RemapShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.math.value.SplitShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.noise.SimplexNoise2DShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.noise.SimplexNoise3DShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.noise.VoronoiDistanceShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.provided.CameraDirectionShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.provided.CameraPositionShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.provided.FragmentCoordinatesShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.provided.InstanceIdShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.provided.PixelSizeShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.provided.SceneColorShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.provided.SceneDepthShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.provided.ScreenPositionShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.provided.TimeGraphShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.shape.CheckerboardShapeShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.shape.EllipseShapeShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.texture.Sampler2DShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.value.ValueBooleanShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.value.ValueColorShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.value.ValueFloatShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.value.ValueVector2ShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.node.value.ValueVector3ShaderNodeBuilder;
import com.gempukku.libgdx.graph.shader.property.ColorShaderPropertyProducer;
import com.gempukku.libgdx.graph.shader.property.FloatShaderPropertyProducer;
import com.gempukku.libgdx.graph.shader.property.GraphShaderPropertyProducer;
import com.gempukku.libgdx.graph.shader.property.TextureShaderPropertyProducer;
import com.gempukku.libgdx.graph.shader.property.Vector2ShaderPropertyProducer;
import com.gempukku.libgdx.graph.shader.property.Vector3ShaderPropertyProducer;

public class GraphShaderConfiguration {
    public static ObjectMap<String, GraphShaderNodeBuilder> graphShaderNodeBuilders = new ObjectMap<>();
    public static Array<GraphShaderPropertyProducer> graphShaderPropertyProducers = new Array<>();

    static {
        // End
        addGraphShaderNodeBuilder(new EndGraphShaderNodeBuilder());

        // Math - Arithmetic
        addGraphShaderNodeBuilder(new AddShaderNodeBuilder());
        addGraphShaderNodeBuilder(new SubtractShaderNodeBuilder());
        addGraphShaderNodeBuilder(new MultiplyShaderNodeBuilder());
        addGraphShaderNodeBuilder(new DivideShaderNodeBuilder());

        // Math - exponential
        addGraphShaderNodeBuilder(new PowerShaderNodeBuilder());
        addGraphShaderNodeBuilder(new ExponentialShaderNodeBuilder());
        addGraphShaderNodeBuilder(new ExponentialBase2ShaderNodeBuilder());
        addGraphShaderNodeBuilder(new NaturalLogarithmShaderNodeBuilder());
        addGraphShaderNodeBuilder(new LogarithmBase2ShaderNodeBuilder());
        addGraphShaderNodeBuilder(new SquareRootShaderNodeBuilder());
        addGraphShaderNodeBuilder(new InverseSquareRootShaderNodeBuilder());

        // Math - Common
        addGraphShaderNodeBuilder(new AbsShaderNodeBuilder());
        addGraphShaderNodeBuilder(new SignShaderNodeBuilder());
        addGraphShaderNodeBuilder(new FloorShaderNodeBuilder());
        addGraphShaderNodeBuilder(new CeilingShaderNodeBuilder());
        addGraphShaderNodeBuilder(new FractionalPartShaderNodeBuilder());
        addGraphShaderNodeBuilder(new ModuloShaderNodeBuilder());
        addGraphShaderNodeBuilder(new MinimumShaderNodeBuilder());
        addGraphShaderNodeBuilder(new MaximumShaderNodeBuilder());
        addGraphShaderNodeBuilder(new MaximumShaderNodeBuilder());
        addGraphShaderNodeBuilder(new ClampShaderNodeBuilder());
        addGraphShaderNodeBuilder(new SaturateShaderNodeBuilder());
        addGraphShaderNodeBuilder(new LerpShaderNodeBuilder());
        addGraphShaderNodeBuilder(new StepShaderNodeBuilder());
        addGraphShaderNodeBuilder(new SmoothstepShaderNodeBuilder());

        // Math - geometric
        addGraphShaderNodeBuilder(new LengthShaderNodeBuilder());
        addGraphShaderNodeBuilder(new DistanceShaderNodeBuilder());
        addGraphShaderNodeBuilder(new DotProductShaderNodeBuilder());
        addGraphShaderNodeBuilder(new CrossProductShaderNodeBuilder());
        addGraphShaderNodeBuilder(new NormalizeShaderNodeBuilder());

        // Math - advanced
        addGraphShaderNodeBuilder(new MergeShaderNodeBuilder());
        addGraphShaderNodeBuilder(new SplitShaderNodeBuilder());
        addGraphShaderNodeBuilder(new RemapShaderNodeBuilder());

        // Math - trigonometry
        addGraphShaderNodeBuilder(new SinShaderNodeBuilder());
        addGraphShaderNodeBuilder(new CosShaderNodeBuilder());
        addGraphShaderNodeBuilder(new TanShaderNodeBuilder());
        addGraphShaderNodeBuilder(new ArcsinShaderNodeBuilder());
        addGraphShaderNodeBuilder(new ArccosShaderNodeBuilder());
        addGraphShaderNodeBuilder(new ArctanShaderNodeBuilder());
        addGraphShaderNodeBuilder(new RadiansShaderNodeBuilder());
        addGraphShaderNodeBuilder(new DegreesShaderNodeBuilder());

        // Math - utilities
        addGraphShaderNodeBuilder(new DistanceFromPlaneShaderNodeBuilder());

        // Lighting
        addGraphShaderNodeBuilder(new CalculateLightingShaderNodeBuilder());
        addGraphShaderNodeBuilder(new ApplyNormalMapShaderNodeBuilder());
        addGraphShaderNodeBuilder(new AmbientLightShaderNodeBuilder());
        addGraphShaderNodeBuilder(new DirectionalLightShaderNodeBuilder());
        addGraphShaderNodeBuilder(new PointLightShaderNodeBuilder());
        addGraphShaderNodeBuilder(new SpotLightShaderNodeBuilder());

        // Effect
        addGraphShaderNodeBuilder(new FresnelEffectShaderNodeBuilder());
        addGraphShaderNodeBuilder(new IntensityShaderNodeBuilder());

        // Attributes
        addGraphShaderNodeBuilder(new AttributePositionShaderNodeBuilder());
        addGraphShaderNodeBuilder(new AttributeNormalShaderNodeBuilder());
        addGraphShaderNodeBuilder(new AttributeTangentShaderNodeBuilder());
        addGraphShaderNodeBuilder(new AttributeUVShaderNodeBuilder());

        // Material
        addGraphShaderNodeBuilder(new FloatAttributeShaderNodeBuilder("Shininess", "Shininess", FloatAttribute.ShininessAlias));
        addGraphShaderNodeBuilder(new FloatAttributeShaderNodeBuilder("AlphaTest", "Alpha test", FloatAttribute.AlphaTestAlias));
        addGraphShaderNodeBuilder(new TextureAttributeShaderNodeBuilder("AmbientTexture", "Ambient texture", TextureAttribute.AmbientAlias));
        addGraphShaderNodeBuilder(new ColorAttributeShaderNodeBuilder("AmbientColor", "Ambient color", ColorAttribute.AmbientAlias));
        addGraphShaderNodeBuilder(new TextureAttributeShaderNodeBuilder("BumpTexture", "Bump texture", TextureAttribute.BumpAlias));
        addGraphShaderNodeBuilder(new TextureAttributeShaderNodeBuilder("DiffuseTexture", "Diffuse texture", TextureAttribute.DiffuseAlias));
        addGraphShaderNodeBuilder(new ColorAttributeShaderNodeBuilder("DiffuseColor", "Diffuse color", ColorAttribute.DiffuseAlias));
        addGraphShaderNodeBuilder(new TextureAttributeShaderNodeBuilder("EmissiveTexture", "Emissive texture", TextureAttribute.EmissiveAlias));
        addGraphShaderNodeBuilder(new ColorAttributeShaderNodeBuilder("EmissiveColor", "Emissive color", ColorAttribute.EmissiveAlias));
        addGraphShaderNodeBuilder(new TextureAttributeShaderNodeBuilder("NormalTexture", "Normal texture", TextureAttribute.NormalAlias));
        addGraphShaderNodeBuilder(new TextureAttributeShaderNodeBuilder("ReflectionTexture", "Reflection texture", TextureAttribute.ReflectionAlias));
        addGraphShaderNodeBuilder(new ColorAttributeShaderNodeBuilder("ReflectionColor", "Reflection color", ColorAttribute.ReflectionAlias));
        addGraphShaderNodeBuilder(new TextureAttributeShaderNodeBuilder("SpecularTexture", "Specular texture", TextureAttribute.SpecularAlias));
        addGraphShaderNodeBuilder(new ColorAttributeShaderNodeBuilder("SpecularColor", "Specular color", ColorAttribute.SpecularAlias));

        // Texture
        addGraphShaderNodeBuilder(new Sampler2DShaderNodeBuilder());

        // Noise
        addGraphShaderNodeBuilder(new SimplexNoise2DShaderNodeBuilder());
        addGraphShaderNodeBuilder(new SimplexNoise3DShaderNodeBuilder());
        addGraphShaderNodeBuilder(new VoronoiDistanceShaderNodeBuilder());

        // Shape
        addGraphShaderNodeBuilder(new CheckerboardShapeShaderNodeBuilder());
        addGraphShaderNodeBuilder(new EllipseShapeShaderNodeBuilder());

        // Provided
        addGraphShaderNodeBuilder(new TimeGraphShaderNodeBuilder());
        addGraphShaderNodeBuilder(new CameraPositionShaderNodeBuilder());
        addGraphShaderNodeBuilder(new CameraDirectionShaderNodeBuilder());
        addGraphShaderNodeBuilder(new FragmentCoordinatesShaderNodeBuilder());
        addGraphShaderNodeBuilder(new SceneDepthShaderNodeBuilder());
        addGraphShaderNodeBuilder(new SceneColorShaderNodeBuilder());
        addGraphShaderNodeBuilder(new ScreenPositionShaderNodeBuilder());
        addGraphShaderNodeBuilder(new PixelSizeShaderNodeBuilder());
        addGraphShaderNodeBuilder(new InstanceIdShaderNodeBuilder());

        // Values
        addGraphShaderNodeBuilder(new ValueBooleanShaderNodeBuilder());
        addGraphShaderNodeBuilder(new ValueColorShaderNodeBuilder());
        addGraphShaderNodeBuilder(new ValueFloatShaderNodeBuilder());
        addGraphShaderNodeBuilder(new ValueVector2ShaderNodeBuilder());
        addGraphShaderNodeBuilder(new ValueVector3ShaderNodeBuilder());

        // Property
        addGraphShaderNodeBuilder(new PropertyShaderNodeBuilder());

        graphShaderPropertyProducers.add(new ColorShaderPropertyProducer());
        graphShaderPropertyProducers.add(new FloatShaderPropertyProducer());
        graphShaderPropertyProducers.add(new Vector2ShaderPropertyProducer());
        graphShaderPropertyProducers.add(new Vector3ShaderPropertyProducer());
        graphShaderPropertyProducers.add(new TextureShaderPropertyProducer());
    }

    private static void addGraphShaderNodeBuilder(GraphShaderNodeBuilder builder) {
        graphShaderNodeBuilders.put(builder.getType(), builder);
    }

    private GraphShaderConfiguration() {

    }
}
