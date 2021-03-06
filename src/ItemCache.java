import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ItemCache<T>
{
    private Date ultimaAtualizacao;
    private String key;
    private T value;

    public ItemCache(String key, T value)
    {
        this.key = key;
        this.update(value);
    }
    public void update(T value)
    {
        this.value = value;
        this.ultimaAtualizacao = new Date();
    }
    public boolean isValid(int validadeSegundos)
    {
        Calendar aux = GregorianCalendar.getInstance();
        aux.setTime( this.ultimaAtualizacao );
        aux.add( GregorianCalendar.SECOND, validadeSegundos );
        Date aux2 = aux.getTime();

        return new Date().before(aux2);
    }
}