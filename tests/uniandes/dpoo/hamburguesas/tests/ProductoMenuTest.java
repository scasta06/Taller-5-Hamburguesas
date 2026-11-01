package uniandes.dpoo.hamburguesas.tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.ArrayList;
import uniandes.dpoo.hamburguesas.mundo.*;
import uniandes.dpoo.hamburguesas.excepciones.*;
public class ProductoMenuTest {

@Test void productoYFactura() {
 ProductoMenu p=new ProductoMenu("Hamburguesa",10000);
 assertEquals("Hamburguesa",p.getNombre()); assertEquals(10000,p.getPrecio());
 assertEquals("Hamburguesa\n            10000\n",p.generarTextoFactura());
 assertEquals(0,new ProductoMenu("Agua",0).getPrecio());
}
}
