module org.syphr.wordplay.core
{
    requires java.base;

    requires lombok;
    requires org.slf4j;

    // automatic modules
    requires com.google.common;
    requires jsr305;

    exports org.syphr.wordplay.core.component;
    exports org.syphr.wordplay.core.config;
    exports org.syphr.wordplay.core.event;
    exports org.syphr.wordplay.core.game;
    exports org.syphr.wordplay.core.lang;
    exports org.syphr.wordplay.core.player;
    exports org.syphr.wordplay.core.space;
}
