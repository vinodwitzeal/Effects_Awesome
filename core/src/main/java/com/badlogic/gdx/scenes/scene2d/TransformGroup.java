package com.badlogic.gdx.scenes.scene2d;

import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class TransformGroup extends Group{
    private float shearY;
    @Override
    protected Matrix4 computeTransform() {
        Affine2 worldTransform = this.worldTransform;
        float originX = this.originX, originY = this.originY;
        worldTransform.setToTrnRotScl(x + originX, y + originY, rotation, scaleX, scaleY);
        if (originX != 0 || originY != 0) worldTransform.translate(-originX, -originY);
        if (shearY!=0) worldTransform.setToShearing(0,shearY);
//        worldTransform.setToShearing(0,-0.1f);

        // Find the first parent that transforms.
        Group parentGroup = parent;
        while (parentGroup != null) {
            if (parentGroup.transform) break;
            parentGroup = parentGroup.parent;
        }
        if (parentGroup != null) worldTransform.preMul(parentGroup.worldTransform);

        computedTransform.set(worldTransform);
        return computedTransform;
    }

  public void setShearY(float shearY){
        this.shearY=shearY;
  }
}
