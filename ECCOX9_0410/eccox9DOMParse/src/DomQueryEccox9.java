package eccox9;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DomQueryEccox9 {
    public static void main(String[] args) {
        try {
            File inputFile = new File("src/XMLeccox9.xml");

            if (!inputFile.exists()) {
                System.out.println("A dokumentum nem található");
                return;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputFile);
            document.getDocumentElement().normalize();

            System.out.println("=== XML LEKÉRDEZÉSEK ===");
            System.out.println();

            lekerdezesGundelSzakacsok(document);
            lekerdezesLeCordonBleuFoszakacsok(document);
            lekerdezesMiskolciVendegekEsRendeleseik(document);

        } catch (Exception e) {
            System.out.println("Hiba történt a lekérdezések végrehajtása közben.");
            e.printStackTrace();
        }
    }

    /**
     * 1. LEKÉRDEZÉS: Keresd meg a 'Gundel' étteremben (Ekod='e2') dolgozó összes Szakács nevét és részlegét!
     */
    private static void lekerdezesGundelSzakacsok(Document doc) {
        System.out.println("1. Lekérdezés: A 'Gundel' étteremben dolgozó szakácsok:");
        System.out.println("--------------------------------------------------------");

        NodeList szakacsLista = doc.getElementsByTagName("Szakacs");
        boolean talalat = false;

        for (int i = 0; i < szakacsLista.getLength(); i++) {
            Node node = szakacsLista.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element szakacsElem = (Element) node;

                if ("e2".equals(szakacsElem.getAttribute("E_SZ"))) {
                    String nev = getText(szakacsElem, "Nev");
                    String reszleg = getText(szakacsElem, "Reszleg");
                    System.out.println(" - Név: " + nev + " | Részleg: " + reszleg);
                    talalat = true;
                }
            }
        }
        if (!talalat) System.out.println("Nem található szakács ebben az étteremben.");
        System.out.println();
    }

    /**
     * 2. LEKÉRDEZÉS: Listázd ki azokat a Főszakácsokat, akiknek a végzettségei között szerepel a 'Paul Bocuse Institute' (vagy Institue)!
     */
    private static void lekerdezesLeCordonBleuFoszakacsok(Document doc) {
        System.out.println("2. Lekérdezés: Paul Bocuse végzettségű főszakácsok:");
        System.out.println("--------------------------------------------------------");

        NodeList foszakacsLista = doc.getElementsByTagName("Foszakacs");

        for (int i = 0; i < foszakacsLista.getLength(); i++) {
            Node node = foszakacsLista.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element foszakacsElem = (Element) node;

                List<String> vegzettsegek = getTextList(foszakacsElem, "Vegzettseg");

                for (String vegzettseg : vegzettsegek) {
                    if (vegzettseg.contains("Paul Bocuse")) {
                        String nev = getText(foszakacsElem, "Nev");
                        String eletkor = getText(foszakacsElem, "Eletkor");
                        System.out.println(" - Név: " + nev + " (" + eletkor + " éves)");
                        break;
                    }
                }
            }
        }
        System.out.println();
    }

    /**
     * 3. LEKÉRDEZÉS: Keresd meg a Miskolcon élő vendégeket, és számold ki a rendeléseik összértékét!
     */
    private static void lekerdezesMiskolciVendegekEsRendeleseik(Document doc) {
        System.out.println("3. Lekérdezés: Miskolci vendégek költései:");
        System.out.println("--------------------------------------------------------");

        NodeList vendegLista = doc.getElementsByTagName("Vendeg");
        NodeList rendelesLista = doc.getElementsByTagName("Rendeles");

        for (int i = 0; i < vendegLista.getLength(); i++) {
            Node vNode = vendegLista.item(i);
            if (vNode.getNodeType() == Node.ELEMENT_NODE) {
                Element vendegElem = (Element) vNode;

                String varos = getText(vendegElem, "Varos");
                if ("Miskolc".equalsIgnoreCase(varos)) {
                    String vkod = vendegElem.getAttribute("Vkod");
                    String nev = getText(vendegElem, "Nev");

                    int osszesKoltseg = 0;
                    int rendelesekSzama = 0;

                    for (int j = 0; j < rendelesLista.getLength(); j++) {
                        Node rNode = rendelesLista.item(j);
                        if (rNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element rendelesElem = (Element) rNode;

                            if (vkod.equals(rendelesElem.getAttribute("E_V_V"))) {
                                String osszegStr = getText(rendelesElem, "Osszeg");
                                osszesKoltseg += Integer.parseInt(osszegStr);
                                rendelesekSzama++;
                            }
                        }
                    }
                    System.out.println(" - " + nev + " (" + vkod + ") -> "
                            + rendelesekSzama + " db rendelés, összesen: " + osszesKoltseg + " Ft");
                }
            }
        }
        System.out.println();
    }

    private static String getText(Element elem, String tag) {
        NodeList nl = elem.getElementsByTagName(tag);
        if (nl != null && nl.getLength() > 0) {
            return nl.item(0).getTextContent().trim();
        }
        return "";
    }
    private static List<String> getTextList(Element input, String tag) {
        List<String> list = new ArrayList<>();
        NodeList lista = input.getElementsByTagName(tag);
        for (int i = 0; i < lista.getLength(); i++) {
            list.add(lista.item(i).getTextContent().trim());
        }
        return list;
    }
}
