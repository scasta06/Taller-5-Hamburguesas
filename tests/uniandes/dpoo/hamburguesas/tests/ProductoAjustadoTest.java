package uniandes.dpoo.hamburguesas.tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.ArrayList;
import uniandes.dpoo.hamburguesas.mundo.*;
import uniandes.dpoo.hamburguesas.excepciones.*;
public class ProductoAjustadoTest {

@Test void sinAjustes() {
 ProductoAjustado p=new ProductoAjustado(new ProductoMenu("Base",10000));
 assertEquals("Base",p.getNombre()); assertEquals(10000,p.getPrecio());
 assertEquals("Base\n            10000\n            10000\n",p.generarTextoFactura());
}
@Test void adicionesYEliminaciones() {
 ProductoMenu base=new ProductoMenu("Base",10000);
 ProductoAjustado p=new ProductoAjustado(base);
 Ingrediente queso=new Ingrediente("queso",1000);
 p.agregarIngrediente(queso);p.agregarIngrediente(queso);
 p.eliminarIngrediente(new Ingrediente("tomate",500));
 assertEquals(12000,p.getPrecio()); assertEquals(10000,base.getPrecio());
 assertEquals("Base\n            10000\n    +queso                1000\n    +queso                1000\n    -tomate\n            12000\n",p.generarTextoFactura());
}
@Test void quitarNoRebaja() {
 ProductoAjustado p=new ProductoAjustado(new ProductoMenu("Agua",3000));
 p.eliminarIngrediente(new Ingrediente("cebolla",1000)); assertEquals(3000,p.getPrecio());
 assertTrue(p.generarTextoFactura().contains("-cebolla"));
}
}
