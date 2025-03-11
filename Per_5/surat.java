
import java.util.List;
import java.util.ArrayList;

public class surat{
    private String surat;
    public void suratPendek(){
            List<String> surat = new ArrayList<>();
        
            surat.add("Al-Ikhlas");
            surat.add("AL-Falaq");
            surat.add("An-nas");
            surat.add("Al-Kafirun");
            surat.add("Al-Kautsar");
            surat.add("Al-Mau'un");

            System.out.println("Surat pendek yang tersedia untuk dibaca:");
            System.out.println(surat);
        }

}
