package io.foodninja.android;


final class Modules {
    static Object[] list() {
        return new Object[] {
                new AndroidModule(),
                new FoodNinjaModule()
        };
    }

    private Modules() {
        // No instances.
    }
}
