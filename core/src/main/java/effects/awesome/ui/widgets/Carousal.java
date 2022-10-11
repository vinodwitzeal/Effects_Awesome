package effects.awesome.ui.widgets;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;
import java.util.List;

public class Carousal extends ScrollPane {
    public float pageWidth, pageHeight, padding;
    public List<Actor> tableActors;
    private Table contentTable;

    public Carousal(float pageWidth,float pageHeight,float padding) {
        this(new Table(),pageWidth,pageHeight,padding);
    }

    public Carousal(Table contentTable,float pageWidth,float pageHeight,float padding) {
        super(contentTable);
        this.pageWidth=pageWidth;
        this.pageHeight=pageHeight;
        this.padding=padding;
        this.contentTable = contentTable;
        setScrollingDisabled(false, true);
        contentTable.pad(0, padding, 0, padding);
        tableActors = new ArrayList<Actor>();
    }

    public void addPage(Actor multiScrollActor) {
        CarousalPage carousalPage=new CarousalPage(padding);
        carousalPage.add(multiScrollActor).expand().fill();
        tableActors.add(carousalPage);
        contentTable.add(carousalPage).width(pageWidth).height(pageHeight);
    }

    @Override
    public void act(float delta) {
        if (!isDragging() && !isPanning() && !isFlinging()) {
            int itemPosition = (int) (getScrollX() / pageWidth);
            scrollTo(itemPosition);
        }
        super.act(delta);
    }

    public void scrollTo(int itemPosition) {
        float scrollX = getScrollX();
        float actorX = getX()+padding + pageWidth * itemPosition;
        if (scrollX < actorX + pageWidth * 0.5f) {
            scrollX(actorX - padding);
        } else if (scrollX > actorX + pageWidth * 0.5f) {
            scrollX(actorX + pageWidth + padding);
        }
    }
}
