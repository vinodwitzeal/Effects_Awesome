package effects.awesome.water;

public class WaterColumn {
    public float targetHeight;
    public float height;
    public float speed;

    public WaterColumn(float targetHeight,float height,float speed){
        this.targetHeight=targetHeight;
        this.height=height;
        this.speed=speed;
    }

    public void update(float dampening, float tension) {
        float x = targetHeight - height;
        speed += tension * x - speed * dampening;
        height += speed;
    }
}
