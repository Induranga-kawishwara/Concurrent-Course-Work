class Person extends Thread {
    private final Bathroom bathroom;
    private final String name;

    public Person(Bathroom bathroom, String name) {
        this.bathroom = bathroom;
        this.name = name;
    }

    @Override
    public void run() {
        bathroom.useBathroom(name);
    }
}