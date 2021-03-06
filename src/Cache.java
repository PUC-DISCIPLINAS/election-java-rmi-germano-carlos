import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class Cache<T> {

    private final Map<String, ItemCache<T>> map = new HashMap<String, ItemCache<T>>();
    private final int validadeSegundos;

    public Cache(int validadeSegundos)
    {
        this.validadeSegundos = validadeSegundos;
        Thread t1 = new Thread(() -> {
            try {
                Limpar();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
    }
    @SuppressWarnings("unchecked")
    public T Get(String key)
    {
        if (map.containsKey(key))
            return (T) map.get(key);

        return null;
    }

    public void Add(String key, T value)
    {
        if (map.containsKey(key))
            map.get(key).update(value);
        else
            map.put(key, new ItemCache<>(key, value));
    }

    private void Limpar() throws InterruptedException {
        //noinspection InfiniteLoopStatement
        while(true) {
            synchronized(this)
            {
                Set<String> keys = map.keySet();
                for (String k : keys) {
                    if (!map.get(k).isValid(validadeSegundos))
                        map.remove(k);
                }
            }

            Thread.sleep(1000);
        }
    }

}
