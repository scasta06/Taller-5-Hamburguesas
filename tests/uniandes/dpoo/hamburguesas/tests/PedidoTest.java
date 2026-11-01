package uniandes.dpoo.hamburguesas.tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.ArrayList;
import uniandes.dpoo.hamburguesas.mundo.*;
import uniandes.dpoo.hamburguesas.excepciones.*;
public class PedidoTest {

private String leer(File f) throws IOException {
 StringBuilder s=new StringBuilder();
 try (BufferedReader r=new BufferedReader(new FileReader(f))) {
  String linea=r.readLine();while(linea!=null){s.append(linea).append("\n");linea=r.readLine();}
 } return s.toString();
}
@Test void vacioEIdentificadores() {
 Pedido p=new Pedido("Ana","Calle 1"); Pedido q=new Pedido("Luis","Calle 2");
 assertEquals(p.getIdPedido()+1,q.getIdPedido());assertEquals("Ana",p.getNombreCliente());
 assertEquals(0,p.getPrecioTotalPedido());
 assertEquals("Cliente: Ana\nDirección: Calle 1\n----------------\n----------------\nPrecio Neto:  0\nIVA:          0\nPrecio Total: 0\n",p.generarTextoFactura());
}
@Test void facturaCompletaYArchivo() throws IOException {
 Pedido p=new Pedido("Ana","Calle 1");
 ProductoMenu base=new ProductoMenu("Base",10000);p.agregarProducto(base);
 ArrayList<ProductoMenu> lista=new ArrayList<ProductoMenu>();lista.add(base);
 p.agregarProducto(new Combo("Combo",0.1,lista));
 ProductoAjustado ajustado=new ProductoAjustado(base);ajustado.agregarIngrediente(new Ingrediente("queso",1000));p.agregarProducto(ajustado);
 assertEquals(35700,p.getPrecioTotalPedido());
 String esperada="Cliente: Ana\nDirección: Calle 1\n----------------\nBase\n            10000\nCombo Combo\n Descuento: 0.1\n            9000\nBase\n            10000\n    +queso                1000\n            11000\n----------------\nPrecio Neto:  30000\nIVA:          5700\nPrecio Total: 35700\n";
 assertEquals(esperada,p.generarTextoFactura());
 File f=File.createTempFile("pedido-",".txt");
 try {p.guardarFactura(f);assertEquals(esperada,leer(f));}finally{f.delete();}
}
@Test void errorAlGuardar() throws IOException {
 File directorio=File.createTempFile("padre-",".tmp");
 try {new Pedido("Ana","Calle 1").guardarFactura(new File(directorio,"factura.txt"));fail("Debe fallar");}
 catch(FileNotFoundException e){assertNotNull(e);}finally{directorio.delete();}
}
}
