package uniandes.dpoo.hamburguesas.tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.ArrayList;
import uniandes.dpoo.hamburguesas.mundo.*;
import uniandes.dpoo.hamburguesas.excepciones.*;
public class ComboTest {

@Test void descuentoYCopia() {
 ArrayList<ProductoMenu> lista=new ArrayList<ProductoMenu>();
 lista.add(new ProductoMenu("Hamburguesa",10000));lista.add(new ProductoMenu("Papas",5000));
 Combo c=new Combo("Especial",0.07,lista);lista.clear();
 assertEquals("Especial",c.getNombre());assertEquals(13950,c.getPrecio());
 assertEquals("Combo Especial\n Descuento: 0.07\n            13950\n",c.generarTextoFactura());
}
@Test void extremos() {
 ArrayList<ProductoMenu> lista=new ArrayList<ProductoMenu>();
 assertEquals(0,new Combo("Vacio",0.07,lista).getPrecio());
 lista.add(new ProductoMenu("Papas",5000));
 assertEquals(5000,new Combo("Sin descuento",0,lista).getPrecio());
 assertEquals(0,new Combo("Gratis",1,lista).getPrecio());
}
}
