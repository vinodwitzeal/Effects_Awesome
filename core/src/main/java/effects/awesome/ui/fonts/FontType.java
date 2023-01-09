package effects.awesome.ui.fonts;

public enum FontType {

    // RBB is Roboto Bold
    DENK("denk"), NOTO("noto"), ROBOTO("roboto"), P_ROBOTO("plainroboto"), AST("ast"), DS("ds"), GOTHAM("gotham"), ROBOTO_M("roboto_m"),
    RBR("rbr"), RBB("rbb"), RBI("rbi"), GR("gr"), ROBOTO_LIGHT_ITALIC("rbli"), ROBOTO_MEDIUM_ITALIC("rbmi"), ROBOTO_MEDIUM("rbm"), ROBOTO_LIGHT("rbl"), ROBOTO_REGULAR("rbreg"),ROBOTO_BOLD("rb_b"),ROBOTO_CONDENSED_ITALIC("rbci");

    private String name;

    FontType(String name) {
        this.name = name;
    }

    public String value() {
        return name;
    }
}
