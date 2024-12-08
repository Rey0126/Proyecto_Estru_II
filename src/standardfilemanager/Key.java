package standardfilemanager;
import java.io.Serializable;

/**
 *
 * @author brhb2
 */
public class Key implements Serializable{
    private String key;
    private int Byteoffset; 

    public Key(String key, int RRN) {
        this.key = key;
        this.Byteoffset = RRN;
    }

    public Key() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getByteoffset() {
        return Byteoffset;
    }

    public void setByteoffset(int Byteoffset) {
        this.Byteoffset = Byteoffset;
    }

    @Override
    public String toString() {
        return "Llave: " + key + " RRN: " + Byteoffset;
    }
    
    
}
