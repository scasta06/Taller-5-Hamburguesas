package uniandes.dpoo.hamburguesas.tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.ArrayList;
import uniandes.dpoo.hamburguesas.mundo.*;
import uniandes.dpoo.hamburguesas.excepciones.*;
public class RestauranteTest {

private File archivo(String nombre){return new File("data/pruebas/"+nombre+".txt");}
private void cargar(Restaurante r,String i,String m,String c) throws Exception {r.cargarInformacionRestaurante(archivo(i),archivo(m),archivo(c));}
@Test void inicialYVacios() throws Exception {
 Restaurante r=new Restaurante();assertNull(r.getPedidoEnCurso());assertTrue(r.getPedidos().isEmpty());
 cargar(r,"vacio","vacio","vacio");assertTrue(r.getIngredientes().isEmpty());assertTrue(r.getMenuBase().isEmpty());assertTrue(r.getMenuCombos().isEmpty());
}
@Test void cargaValida() throws Exception {
 Restaurante r=new Restaurante();cargar(r,"ingredientes","menu","combos");
 assertEquals(2,r.getIngredientes().size());assertEquals("queso",r.getIngredientes().get(0).getNombre());assertEquals(1000,r.getIngredientes().get(0).getCostoAdicional());
 assertEquals(2,r.getMenuBase().size());assertEquals("Base",r.getMenuBase().get(0).getNombre());assertEquals(5000,r.getMenuBase().get(1).getPrecio());
 assertEquals(2,r.getMenuCombos().size());assertEquals("Especial",r.getMenuCombos().get(0).getNombre());assertEquals(13950,r.getMenuCombos().get(0).getPrecio());assertEquals(5000,r.getMenuCombos().get(1).getPrecio());
}
@Test void cicloPedidos() throws Exception {
 Restaurante r=new Restaurante();cargar(r,"ingredientes","menu","combos");
 r.iniciarPedido("Ana","Calle 1");Pedido p=r.getPedidoEnCurso();assertEquals("Ana",p.getNombreCliente());
 try{r.iniciarPedido("Luis","Calle 2");fail("Ya hay pedido");}catch(YaHayUnPedidoEnCursoException e){assertNotNull(e.getMessage());}
 assertSame(p,r.getPedidoEnCurso());p.agregarProducto(r.getMenuCombos().get(0));
 r.cerrarYGuardarPedido();assertNull(r.getPedidoEnCurso());assertEquals(1,r.getPedidos().size());assertSame(p,r.getPedidos().get(0));
 File f=new File("facturas/factura_"+p.getIdPedido()+".txt");
 try(BufferedReader br=new BufferedReader(new FileReader(f))){assertEquals("Cliente: Ana",br.readLine());}finally{f.delete();}
 r.iniciarPedido("Luis","Calle 2");Pedido q=r.getPedidoEnCurso();r.cerrarYGuardarPedido();assertEquals(2,r.getPedidos().size());
 boolean encontrado=false;for(Pedido anterior:r.getPedidos())if(anterior.getIdPedido()==p.getIdPedido()){assertSame(p,anterior);encontrado=true;}assertTrue(encontrado);
 new File("facturas/factura_"+q.getIdPedido()+".txt").delete();
}
@Test void cerrarSinPedido() throws Exception {
 try{new Restaurante().cerrarYGuardarPedido();fail("No hay pedido");}catch(NoHayPedidoEnCursoException e){assertNotNull(e.getMessage());}
}
@Test void ingredienteRepetido() throws Exception {
 try{cargar(new Restaurante(),"ingredientes_repetidos","menu","combos");fail("Duplicado");}catch(IngredienteRepetidoException e){assertNotNull(e.getMessage());}
}
@Test void productoRepetido() throws Exception {
 try{cargar(new Restaurante(),"ingredientes","menu_repetido","combos");fail("Duplicado");}catch(ProductoRepetidoException e){assertNotNull(e.getMessage());}
}
@Test void comboRepetido() throws Exception {
 try{cargar(new Restaurante(),"ingredientes","menu","combos_repetidos");fail("Duplicado");}catch(ProductoRepetidoException e){assertNotNull(e.getMessage());}
}
@Test void productoFaltante() throws Exception {
 try{cargar(new Restaurante(),"ingredientes","menu","combo_faltante");fail("Faltante");}catch(ProductoFaltanteException e){assertNotNull(e.getMessage());}
}
@Test void numerosInvalidos() throws Exception {
 String[][] casos={{"ingrediente_invalido","menu","combos"},{"ingredientes","menu_invalido","combos"},{"ingredientes","menu","combo_invalido"}};
 for(String[] caso:casos){try{cargar(new Restaurante(),caso[0],caso[1],caso[2]);fail("Numero inválido");}catch(NumberFormatException e){assertNotNull(e);}}
}
@Test void archivosAusentes() throws Exception {
 String[][] casos={{"ausente","menu","combos"},{"ingredientes","ausente","combos"},{"ingredientes","menu","ausente"}};
 for(String[] caso:casos){try{cargar(new Restaurante(),caso[0],caso[1],caso[2]);fail("Archivo ausente");}catch(IOException e){assertNotNull(e);}}
}

    @Test
    void cargarDatosOriginales() throws Exception
    {
        Restaurante restaurante = new Restaurante();
        restaurante.cargarInformacionRestaurante(new File("data/ingredientes.txt"),
                new File("data/menu.txt"), new File("data/combos.txt"));
        assertFalse(restaurante.getIngredientes().isEmpty());
        assertFalse(restaurante.getMenuBase().isEmpty());
        assertFalse(restaurante.getMenuCombos().isEmpty());
    }

    @Test
    void falloDeGuardadoConservaPedido() throws Exception
    {
        Restaurante restaurante = new Restaurante();
        restaurante.iniciarPedido("Ana", "Calle 1");
        Pedido pedido = restaurante.getPedidoEnCurso();
        File carpeta = new File("facturas");
        if (!carpeta.exists())
            assertTrue(carpeta.mkdir());
        File destino = new File(carpeta, "factura_" + pedido.getIdPedido() + ".txt");
        assertTrue(destino.mkdir(), "El escenario necesita un destino nuevo");
        try
        {
            try
            {
                restaurante.cerrarYGuardarPedido();
                fail("Debe lanzar IOException al intentar escribir en una carpeta");
            }
            catch (IOException e)
            {
                assertSame(pedido, restaurante.getPedidoEnCurso());
                assertTrue(restaurante.getPedidos().isEmpty());
            }
        }
        finally
        {
            assertTrue(destino.delete());
        }
    }
}
