package effects.awesome.ui.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class BoardImage extends Widget {
    private Pixmap pixmap;
    private Texture texture;
    private Deque<PixmapDrawer> drawers;
    private List<PixmapDrawer> drawerList;
    private int drawerPad;
    private int drawerSize;
    private int halfDrawerSize;
    private int oneHalfDrawerSize;
    private Pixmap whitePixmap,greenWaypoint,yellowWaypoint,redWaypoint,blueWaypoint;
    private Pixmap greenNextWaypoint,yellowNextWaypoint,redNextWaypoint,blueNextWaypoint;
    private float currentTime;
    private float duration=0.1f;
    public BoardImage(int width, int height) {
        setSize(width, height);
        drawerPad = 5;
        drawerSize = 64;
        halfDrawerSize = 32;
        oneHalfDrawerSize = 96;
        pixmap = new Pixmap(626, 626, Pixmap.Format.RGBA8888);
        drawers = new ArrayDeque<PixmapDrawer>();
        drawerList=new ArrayList<PixmapDrawer>();
        whitePixmap=getPixmap("white_waypoint");
        greenWaypoint=getPixmap("green_waypoint");
        yellowWaypoint=getPixmap("yellow_waypoint");
        redWaypoint=getPixmap("red_waypoint");
        blueWaypoint=getPixmap("blue_waypoint");

        greenNextWaypoint=getPixmap("green_next");
        yellowNextWaypoint=getPixmap("yellow_next");
        redNextWaypoint=getPixmap("red_next");
        blueNextWaypoint=getPixmap("blue_next");
        Pixmap commonStop=getPixmap("common_stop");
        Pixmap rounded1=getPixmap("rounded_waypoint_1");
        Pixmap rounded2=getPixmap("rounded_waypoint_2");
        Pixmap rounded3=getPixmap("rounded_waypoint_3");
        Pixmap rounded4=getPixmap("rounded_waypoint_4");
        addDrawers();

//        drawerList.get(27).pixmap=yellowNextWaypoint;
//        drawerList.get(25).pixmap=getPixmap("yellow_stop");
        drawerList.get(6).pixmap=redNextWaypoint;
        drawerList.get(4).pixmap=getPixmap("red_stop");
//        drawerList.get(13).pixmap=greenNextWaypoint;
//        drawerList.get(11).pixmap=getPixmap("green_stop");
        drawerList.get(20).pixmap=blueNextWaypoint;
        drawerList.get(18).pixmap=getPixmap("blue_stop");
//        drawerList.get(1).pixmap=commonStop;
//        drawerList.get(8).pixmap=commonStop;
//        drawerList.get(15).pixmap=commonStop;
//        drawerList.get(22).pixmap=commonStop;

        drawerList.get(2).pixmap=rounded1;
        drawerList.get(3).pixmap=rounded3;
        drawerList.get(9).pixmap=rounded4;
        drawerList.get(10).pixmap=rounded2;
        drawerList.get(16).pixmap=rounded3;
        drawerList.get(17).pixmap=rounded1;
        drawerList.get(23).pixmap=rounded2;
        drawerList.get(24).pixmap=rounded4;


        Pixmap centerPixmap=getPixmap("center");
        pixmap.drawPixmap(centerPixmap, 212, 212);

    }

    private Pixmap getPixmap(String name){
        Texture waypointTexture=new Texture("ludo/"+name+".png");
        waypointTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureData textureData=waypointTexture.getTextureData();
        textureData.prepare();
        return textureData.consumePixmap();
    }

    private void addDrawers() {
        int centerX = 313;
        int centerY = 313;
        int width = 626;
        int height = 626;
        Position position = new Position(centerX - (oneHalfDrawerSize + drawerPad), 0);

        addDrawer(position, 3, 1, 0);

        position.x = 2 * (drawerSize + drawerPad);
        position.y = centerY - (oneHalfDrawerSize + drawerPad);

        addDrawer(position, 3, 0, -1);

        position.x = 0;
        position.y = centerY - halfDrawerSize;

        Position leftStart = new Position(position.x + drawerSize + drawerPad, position.y);

        addDrawer(position, 2, 1, 0);

        position.x = drawerSize + drawerPad;
        position.y = centerY + halfDrawerSize + drawerPad;

        addDrawer(position, 2, 0, 1);

        position.x = centerX - (oneHalfDrawerSize + drawerPad);
        position.y = height - (3 * drawerSize + 2 * drawerPad);

        addDrawer(position, 3, 1, 0);

        position.x = centerX - halfDrawerSize;
        position.y = height - (2 * drawerSize + drawerPad);

        Position topStart = new Position(position);

        position.y = height - drawerSize;

        addDrawer(position, 2, 0, 1);

        position.x = centerX + (halfDrawerSize + drawerPad);
        position.y = topStart.y;

        addDrawer(position, 2, -1, 0);

        position.x = width - (3 * drawerSize + 2 * drawerPad);
        position.y = centerY + (halfDrawerSize + drawerPad);

        addDrawer(position, 3, 0, 1);

        position.x = width - (2 * drawerSize + drawerPad);
        position.y = centerY - halfDrawerSize;

        Position rightStart = new Position(position);

        position.x = width - drawerSize;

        addDrawer(position, 2, -1, 0);

        position.x = rightStart.x;
        position.y = centerY - (oneHalfDrawerSize + drawerPad);

        addDrawer(position, 2, 0, -1);

        position.x = centerX + (halfDrawerSize + drawerPad);
        position.y = 2 * (drawerSize + drawerPad);

        addDrawer(position, 3, -1, 0);

        position.x = centerX - halfDrawerSize;
        position.y = drawerSize + drawerPad;

        Position bottomStart = new Position(position);

        position.y = 0;
        addDrawer(position, 1, 0, -1);

        addDrawer(bottomStart, 2, 1, 0,whitePixmap);
        addDrawer(leftStart, 2, 0, 1,redWaypoint);
        addDrawer(topStart, 2, -1, 0,whitePixmap);
        addDrawer(rightStart, 2, 0, -1,blueWaypoint);
    }


    private void addDrawer(Position position, int count, int vertical, int horizontal) {
        addDrawer(position,count,vertical,horizontal, whitePixmap);
    }

    private void addDrawer(Position position, int count, int vertical, int horizontal,Pixmap pixmap) {
        for (int i = 0; i < count; i++) {
            PixmapDrawer pixmapDrawer = new PixmapDrawer(pixmap);
            pixmapDrawer.x = position.x;
            pixmapDrawer.y = position.y;
            drawers.add(pixmapDrawer);
            drawerList.add(pixmapDrawer);
            position.x = position.x + horizontal * (drawerSize + drawerPad);
            position.y = position.y + vertical * (drawerSize + drawerPad);
        }
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (drawers!=null){
            currentTime+=Gdx.graphics.getDeltaTime();
            if (drawers.size() > 0 && currentTime>duration) {
                currentTime=0.0f;
                PixmapDrawer drawer = drawers.removeFirst();
                drawer.draw(pixmap);
                if (texture != null) {
                    texture.dispose();
                }
                texture = new Texture(pixmap);
            }

            if (drawers.size()==0){
                PixmapIO.writePNG(Gdx.files.local("ludo_board.png"),pixmap);
                drawers=null;
            }
        }

        if (texture != null) {
            batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        }
    }

    public class PixmapDrawer {
        private int x, y;
        private int topLeft, topRight, bottomRight, bottomLeft;
        private int width, height;
        private Color color;
        private Pixmap pixmap;

        public PixmapDrawer(Pixmap pixmap) {
            topLeft = 4;
            topRight = 4;
            bottomRight = 4;
            bottomLeft = 4;
            width = 64;
            height = 64;
            color = Color.valueOf("ffffff");
            this.pixmap=pixmap;
        }

        private Pixmap createPixmap() {
//            pixmap= waypointTexture.getTextureData().consumePixmap();
//            Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
//            pixmap.setColor(color);
//            pixmap.setFilter(Pixmap.Filter.BiLinear);
//
//            pixmap.fill();

//            //Top-Left
//            int topLeftWidth = topLeft;
//            int topLeftHeight = height - (topLeft + bottomLeft);
//            pixmap.fillRectangle(0, height - bottomLeft, topLeftWidth, topLeftHeight);
//            pixmap.fillCircle(topLeft, topLeft, topLeft);
//
//            //Top-Right
//            int topRightWidth = width - (topLeft + topRight);
//            int topRightHeight = topRight;
//            pixmap.fillRectangle(topLeft, topRight, topRightWidth, topRightHeight);
//            pixmap.fillCircle(width - topRight, topRight, topRight);
//
//            //Bottom-Right
//            int bottomRightHeight = height - topRight;
//            int bottomRightWidth = bottomRight;
//            pixmap.fillRectangle(width - bottomRight, height, bottomRightWidth, bottomRightHeight);
//            pixmap.fillCircle(width - bottomRight, height - bottomRight, bottomRight);
//
//            //Bottom-Left
//            int bottomLeftWidth = width - (bottomLeft + bottomRight);
//            int bottomLeftHeight = bottomLeftWidth;
//            pixmap.fillRectangle(bottomLeft, height, bottomLeftWidth, bottomLeftHeight);
//            pixmap.fillCircle(bottomLeft, height - bottomLeft, bottomLeft);
//
//            //Center
//            int centerWidth=width-(topLeft+topRight);

            return pixmap;
        }

        public void draw(Pixmap pixmap) {
//            Pixmap childPixmap = createPixmap();
            pixmap.drawPixmap(this.pixmap, x, y);
        }
    }


    public class Position {
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Position(Position position) {
            this.x = position.x;
            this.y = position.y;
        }
    }
}
