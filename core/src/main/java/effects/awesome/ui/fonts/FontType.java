package effects.awesome.ui.fonts;

public enum FontType {
    Noto("noto"),Gothic("gothic"),Arial("arial");

    private String name;
    FontType(String name){
        this.name=name;
    }

    public String getName(){
        return this.name;
    }
}
