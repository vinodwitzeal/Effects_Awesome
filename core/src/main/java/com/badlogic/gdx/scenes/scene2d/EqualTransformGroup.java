package com.badlogic.gdx.scenes.scene2d;

public class EqualTransformGroup extends TransformGroup{
    public TransformGroup child;
    public EqualTransformGroup(){
        child=new TransformGroup();
        addActor(child);
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        child.setSize(width,height);
    }

    @Override
    public void setOrigin(float originX, float originY) {
        super.setOrigin(originX, originY);
        child.setOrigin(originX,originY);
    }

    @Override
    public void setShearY(float shearY) {
//        super.setShearY(shearY);
        child.setShearY(-shearY);
    }
}
