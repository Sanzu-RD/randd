/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.1
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package particles;

import com.badlogic.gdx.math.Matrix4;

import particles.EffekseerCore.TypeOpenGL;

class EffekseerManagerCore {
	public transient long swigCPtr;
	protected transient boolean swigCMemOwn;
	
	protected EffekseerManagerCore(long cPtr, boolean cMemoryOwn) {
		swigCMemOwn = cMemoryOwn;
		swigCPtr = cPtr;
	}
	
	protected static long getCPtr(EffekseerManagerCore obj) {
		return (obj == null) ? 0 : obj.swigCPtr;
	}
	
	@SuppressWarnings("deprecation")
	protected void finalize() {
		delete();
	}
	
	public synchronized void delete() {
		if(swigCPtr != 0) {
			if(swigCMemOwn) {
				swigCMemOwn = false;
				EffekseerCoreJNI.delete_EffekseerManagerCore(swigCPtr);
			}
			swigCPtr = 0;
		}
	}
	
	public EffekseerManagerCore() {
		this(EffekseerCoreJNI.new_EffekseerManagerCore(), true);
	}
	
	public boolean Initialize(int spriteMaxCount, EffekseerCore.TypeOpenGL typeOpenGL) {
		return EffekseerCoreJNI.EffekseerManagerCore_Initialize(swigCPtr, this, spriteMaxCount, typeOpenGL.getId());
	}
	
	public void Update(float deltaFrames) {
		EffekseerCoreJNI.EffekseerManagerCore_Update(swigCPtr, this, deltaFrames);
	}
	
	public int Play(EffekseerEffectCore effect) {
		return EffekseerCoreJNI.EffekseerManagerCore_Play(swigCPtr, this, EffekseerEffectCore.getCPtr(effect), effect);
	}
	
	public void SetEffectPosition(int handle, float x, float y, float z) {
		EffekseerCoreJNI.EffekseerManagerCore_SetEffectPosition(swigCPtr, this, handle, x, y, z);
	}
	
	public void DrawBack() {
		EffekseerCoreJNI.EffekseerManagerCore_DrawBack(swigCPtr, this);
	}
	
	public void DrawFront() {
		EffekseerCoreJNI.EffekseerManagerCore_DrawFront(swigCPtr, this);
	}
	
	public void pause(int handle) {
		EffekseerCoreJNI.EffekseerManagerCore_Pause(swigCPtr, this, handle);
	}
	
	public void resume(int handle) {
		EffekseerCoreJNI.EffekseerManagerCore_Resume(swigCPtr, this, handle);
	}
	
	public void setProjectionMatrix(Matrix4 matrix4, Matrix4 matrix4c, boolean view3D, float wiht, float heit) {
		EffekseerCoreJNI.EffekseerManagerCore_SetProjectionMatrix(swigCPtr, this, matrix4.getValues(), matrix4c.getValues(), view3D, wiht, heit);
	}
	
	public boolean isPlaying(int handle) {
		return EffekseerCoreJNI.EffekseerManagerCore_Isplaying(swigCPtr, this, handle);
		
	}
	
}
