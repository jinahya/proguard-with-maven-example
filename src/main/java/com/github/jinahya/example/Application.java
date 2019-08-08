package com.github.jinahya.example;

public class Application {

    private static final Object private_static = "private_static";

    static final Object _static = "_static";

    protected static final Object protected_static = "protected_static";

    public static final Object public_static = "public_static";

    private static void private_static() {
        System.out.println("private_static: " + private_static);
    }

    static void _static() {
        System.out.println("_static: " + _static);
    }

    protected static void protected_static() {
        System.out.println("protected_static: " + protected_static);
    }

    public static void public_static() {
        System.out.println("public_static: " + public_static);
    }

    public static void main(final String[] args) {
        private_static();
        _static();
        protected_static();
        public_static();
        final Application application = new Application();
        application.public_instance();
        application._instance();
        application.protected_instance();
        application.public_instance();
    }

    public void public_instance() {
        System.out.println(public_instance);
    }

    protected void protected_instance() {
        System.out.println(protected_instance);
    }

    void _instance() {
        System.out.println(_instance);
    }

    private void private_instance() {
        System.out.println(private_instance);
    }

    private Object private_instance = null;

    Object _instance = null;

    protected Object protected_instance = null;

    public Object public_instance = null;
}
