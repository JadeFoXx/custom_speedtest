package ludwigsamuel.custom_speedtest.ui;



public interface Pushable<V> {

    public void setMultiplier(Double mul);

    public Double getMultiplier();

    public void push(V value);

    public void reset();
}
