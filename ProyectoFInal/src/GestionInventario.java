import java.util.Scanner;

public class GestionInventario {
    public static void main(String[] args) {
        Scanner Registrar = new Scanner(System.in);
        String[] Producto = new String[100]; // Guardamos los nombres en esta variable suponiendo un maximo de 100 productos diferentes
        int[] CantidadxProducto = new int[100]; //Guardamos las cantidades en esta variable suponiendo un maximo de 100 cantidades diferentes
        String[][] RegistroxVentas = new String[100][2]; // Arreglo doble para almacenar Nombre y Cantidad en el registro de Venta
        int ContadorProductos = 0;
        int ContadorVentas = 0;

        while (true) {
            System.out.println("Bienvenido al sistema de gestión de inventario de BurgerHouse");
            System.out.println("1. Registrar producto");
            System.out.println("2. Realizar venta");
            System.out.println("3. Ver inventario");
            System.out.println("4. Revisar Stock Minimo");
            System.out.println("5. Ver registro de ventas");
            System.out.print("Ingrese su opción: ");
            int OpcionElegida = Registrar.nextInt();
            Registrar.nextLine(); // Limpiar el buffer del scanner

            switch (OpcionElegida) {
                case 1: //Caso para registrar un producto
                    System.out.println("Registro de Producto");
                    System.out.print("Ingrese el nombre del producto: ");
                    String NombreProducto = Registrar.nextLine(); //Creamos la variable y guardamos el nombre
                    int IdProducto = -1;  //Creamos un identificador del producto para el caso 1
                    for (int i = 0; i < ContadorProductos; i++) { //Buscamos si el producto ingresado ya esta en el sistema
                        if (Producto[i].equals(NombreProducto)) {
                            IdProducto = i;
                        }
                    }
                    if (IdProducto != -1) { //Si esque esta se ejecuta este if para reemplazar el producto ya ingresado
                        System.out.println("El producto "+NombreProducto+" ya existe.");
                        System.out.println("Ingrese la cantidad a agregar");
                        int CantidadxSumar = Registrar.nextInt(); //Creamos la variable para la cantidad que se debe sumar
                        CantidadxProducto[IdProducto] = CantidadxProducto[IdProducto] + CantidadxSumar;
                        System.out.println("La nueva cantidad para el producto "+NombreProducto+" es " + CantidadxProducto[IdProducto]);
                        break;
                    }else { //Si no esta se agrega el producto al sistema
                        Producto[ContadorProductos] = NombreProducto;  //Colocamos el nombre de producto en una posicion dentro de la cantidad total de productos
                        System.out.print("Ingrese la cantidad a agregar: ");
                        int CantidadxIngresar = Registrar.nextInt();  //Creamos la variable y guardamos la cantidad
                        Registrar.nextLine(); // Limpiar el buffer del scanner
                        CantidadxProducto[ContadorProductos] = CantidadxIngresar; //Colocamos la cantidad en una posicion dentro de la cantidad total de productos
                        ContadorProductos++; //Cambiamos el contador para el proximo producto
                        System.out.println("Producto registrado con éxito.\n");
                        break;
                    }

                case 2:
                    // Código para realizar venta
                    System.out.println("Realizar Venta");
                    System.out.print("Ingrese el nombre del producto vendido: ");
                    String NombreProductoVendido = Registrar.nextLine(); //Creamos la variable y guardamos el nombre del producto vendido
                    int IdVenta = -1; //Creamos un ID para identificar la posicion del producto en la venta
                    for (int i = 0; i < ContadorProductos; i++) { //Buscamos si el producto existe, en caso de que no, se cancela la busqueda
                        if (Producto[i].equals(NombreProductoVendido)) {
                            IdVenta = i;
                            break;
                        }
                    }
                    if (IdVenta != -1) { //Si esque si existe se procede a realizar la venta
                        System.out.print("Ingrese la cantidad vendida: ");
                        int cantidadVendida = Registrar.nextInt(); //Creamos la variable para guardar la cantidad a vender
                        Registrar.nextLine(); // Limpiar el buffer del scanner
                        if (cantidadVendida <= CantidadxProducto[IdVenta]) { //Comprobamos que la cantidad a vender no sea mayor a la que existe para el producto
                            CantidadxProducto[IdVenta] -= cantidadVendida; //Restamos la cantidad vendida de la cantidad total
                            System.out.println("Venta realizada con éxito.\n");
                            // Registro de la venta / Este codigo es para crear el registro de las ventas hechas
                            boolean ProductoExistente = false; //El boolean es para verificar si el producto ya existe, en caso de que si solo se edita la cantidad y no se crea un registro nuevo
                            for (int i = 0; i < ContadorVentas; i++) { //For para iterar
                                if (RegistroxVentas[i][0].equals(NombreProductoVendido)) { // Verificar si el producto ya existe en el registro de ventas
                                    int cantidadAnterior = Integer.parseInt(RegistroxVentas[i][1]); //Creamos una variable para guardar la cantidad del producto convertido en int
                                    RegistroxVentas[i][1] = String.valueOf(cantidadAnterior + cantidadVendida); // Restamos la cantidad vendida con la cantidad anterior y la reemplazamos en el producto convirtiendola en String
                                    ProductoExistente = true;
                                    break;
                                }
                            }
                            if (!ProductoExistente) { // Si el producto no existe en el registro de ventas, agregar una nueva entrada
                                RegistroxVentas[ContadorVentas][0] = NombreProductoVendido; //Agregamos el nombre del producto vendido ingresado por el cliente al primer espacio (Nombre)
                                RegistroxVentas[ContadorVentas][1] = String.valueOf(cantidadVendida); //Agregamos la cantidad del producto vendido ingresado por el cliente al segundo espacio (Cantidad)
                                ContadorVentas++; // Incrementar el contador de ventas realizadas para el proximo producto
                            }
                        } else {
                            System.out.println("No hay suficientes unidades para realizar la venta.\n");
                        }
                    } else {
                        System.out.println("El producto ingresado no existe en el inventario.\n");
                    }
                    break;
                case 3: //Desplegamos el inventario Actual
                    System.out.println("Inventario Actual");
                    for (int i = 0; i < ContadorProductos; i++) { //iteramos en todos los productos ingresados
                        System.out.println("Producto: " + Producto[i] + ", Cantidad: " + CantidadxProducto[i]);
                    }
                    System.out.println();
                    break;

                case 4:
                    System.out.println("Ingresa el stock minimo: ");
                    int stockMinimo = Registrar.nextInt();

                    boolean hayProductosBajoStock = false; //Boolean para verificar si el stock esta por debajo de la cantidad deseada
                    for (int i = 0; i < ContadorProductos; i++) {
                        if (CantidadxProducto[i] <= stockMinimo) {
                            System.out.println("Estos productos tienen un stock menor a " +stockMinimo+ " unidades");
                            System.out.println("Producto: " + Producto[i] + ", Cantidad: " + CantidadxProducto[i]);
                            hayProductosBajoStock = true;
                        }
                    }
                    if (!hayProductosBajoStock) { //Si no hay productos menores a la cantidad deseada se ejecuta
                        System.out.println("No hay productos con stock por debajo de "+stockMinimo+" unidades\n");
                    }
                    System.out.println();
                    break;
                case 5:
                    // Mostrar registro de ventas
                    System.out.println("Registro de Ventas");
                    if (ContadorVentas == 0) {
                        System.out.println("No hay ventas registradas.");
                    } else {
                        for (int i = 0; i < ContadorVentas; i++) {
                            System.out.println("Venta " + (i + 1) + ": Producto: " + RegistroxVentas[i][0] + ", Cantidad vendida: " + RegistroxVentas[i][1]);
                        }
                    }
                    break;

                default:
                    System.out.println("Opción no válida. Por favor, ingrese una opción válida.\n");
                    break;
            }
        }
    }
}
