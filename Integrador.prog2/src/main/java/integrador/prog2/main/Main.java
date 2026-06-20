package integrador.prog2.main;

import enums.Estado;
import enums.FormaPago;
import integrador.prog2.entities.Categoria;
import integrador.prog2.entities.Pedido;
import integrador.prog2.entities.Producto;
import integrador.prog2.entities.Usuario;
import integrador.prog2.exception.EntidadNoEncontradaException;
import integrador.prog2.services.CategoriaService;
import integrador.prog2.services.PedidoService;
import integrador.prog2.services.ProductoService;
import integrador.prog2.services.UsuarioService;
import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final CategoriaService categoriaService = new CategoriaService();
    private static final ProductoService productoService = new ProductoService();
    private static final Scanner scanner = new Scanner(System.in);
    private static final PedidoService pedidoService = new PedidoService();
    private static final UsuarioService usuarioService = new UsuarioService();

    public static void main(String[] args) {

        try {
            categoriaService.crearCategoria("Hamburguesas", "Variedad de hamburguesas");
            categoriaService.crearCategoria("Bebidas", "Gaseosas y jugos");
        } catch (Exception ignored) {
        }

        int opcion;

        do {
            System.out.println("\n=== FOOD STORE ===");
            System.out.println("1. Categorias");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios"); 
            System.out.println("4. Pedidos");  
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        menuCategorias();
                        break;
                    case 2:
                        menuProductos();
                        break;
                    case 3:
                        menuUsuarios();
                        break;
                    case 4:
                        menuPedidos();
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un numero valido.");
                opcion = -1;
            }
        } while (opcion != 0);

    }


    private static void menuCategorias() {
        int opcion;
        do {
            System.out.println("\n=== MENU CATEGORIAS ===");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar"); // Vuelve a ser el 4
            System.out.println("0. Volver");
            System.out.print("Seleccione una opcion: ");

            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1: uiListarCategorias(); break;
                case 2: uiCrearCategoria(); break;
                case 3: uiEditarCategoria(); break;
                case 4: uiEliminarCategoria(); break;
                case 0: break;
                default: System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }


    private static void menuProductos() {

        int opcion;

        do {

            System.out.println("\n=== MENU PRODUCTOS ===");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opcion: ");

            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {

                case 1:
                    uiListarProductos();
                    break;

                case 2:
                    uiCrearProducto();
                    break;

                case 3:
                    uiEditarProducto();
                    break;

                case 4:
                    uiEliminarProducto();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opcion invalida.");

            }

        } while (opcion != 0);

    }


    private static void uiListarCategorias() {

        List<Categoria> lista = categoriaService.listarCategorias();

        if (lista.isEmpty()) {

            System.out.println("No hay categorias activas.");

        } else {

            System.out.println("\n--- LISTADO DE CATEGORIAS ---");

            for (Categoria c : lista) {

                System.out.println(c);

            }

        }

    }

    private static void uiCrearCategoria() {

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Descripcion: ");
        String descripcion = scanner.nextLine();

        try {

            Categoria categoria =
                    categoriaService.crearCategoria(nombre, descripcion);

            System.out.println(
                    "Categoria creada con ID: " + categoria.getId());

        } catch (IllegalArgumentException e) {

            System.out.println(e.getMessage());

        }

    }

    private static void uiEditarCategoria() {

        try {

            System.out.print("ID de categoria: ");
            Long id = Long.parseLong(scanner.nextLine());

            System.out.print("Nuevo nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Nueva descripcion: ");
            String descripcion = scanner.nextLine();

            categoriaService.editarCategoria(id, nombre, descripcion);

            System.out.println("Categoria modificada correctamente.");

        } catch (NumberFormatException e) {

            System.out.println("El ID debe ser numerico.");

        } catch (EntidadNoEncontradaException | IllegalArgumentException e) {

            System.out.println(e.getMessage());

        }

    }

    private static void uiEliminarCategoria() {

        try {

            System.out.print("ID de categoria: ");
            Long id = Long.parseLong(scanner.nextLine());

          
            System.out.print("¿Esta seguro que desea eliminar la categoria? (S/N): ");
            String confirmacion = scanner.nextLine();

            if (confirmacion.equalsIgnoreCase("S")) {
                categoriaService.eliminarCategoria(id);
                System.out.println("Categoria eliminada correctamente.");
            } else {
                System.out.println("Eliminacion cancelada por el usuario.");
            }
            

        } catch (NumberFormatException e) {

            System.out.println("El ID debe ser numerico.");

        } catch (EntidadNoEncontradaException e) {

            System.out.println(e.getMessage());

        }

    }

    private static void uiListarProductos() {

        List<Producto> lista = productoService.listarProductos();

        if (lista.isEmpty()) {

            System.out.println("No hay productos cargados.");

        } else {

            System.out.println("\n--- LISTADO DE PRODUCTOS ---");

            for (Producto p : lista) {

                System.out.println(p);

            }

        }

    }

    private static void uiCrearProducto() {

        try {

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Descripcion: ");
            String descripcion = scanner.nextLine();

            System.out.print("Precio: ");
            Double precio = Double.parseDouble(scanner.nextLine());

            System.out.print("Stock: ");
            int stock = Integer.parseInt(scanner.nextLine());

            System.out.print("Imagen: ");
            String imagen = scanner.nextLine();

            System.out.print("Disponible (true/false): ");
            boolean disponible = Boolean.parseBoolean(scanner.nextLine());

            uiListarCategorias();

            System.out.print("ID de categoria: ");
            Long idCategoria = Long.parseLong(scanner.nextLine());

            Categoria categoria =
                    categoriaService.buscarPorId(idCategoria);

            Producto producto =
                    productoService.crearProducto(
                            nombre,
                            precio,
                            descripcion,
                            stock,
                            imagen,
                            disponible,
                            categoria);

            System.out.println(
                    "Producto creado con ID: " + producto.getId());

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    private static void uiEditarProducto() {

        try {

            uiListarProductos();

            System.out.print("ID del producto: ");
            Long idProducto = Long.parseLong(scanner.nextLine());

            System.out.print("Nuevo nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Nueva descripcion: ");
            String descripcion = scanner.nextLine();

            System.out.print("Nuevo precio: ");
            Double precio = Double.parseDouble(scanner.nextLine());

            System.out.print("Nuevo stock: ");
            int stock = Integer.parseInt(scanner.nextLine());

            System.out.print("Nueva imagen: ");
            String imagen = scanner.nextLine();

            System.out.print("Disponible (true/false): ");
            boolean disponible = Boolean.parseBoolean(scanner.nextLine());

            uiListarCategorias();

            System.out.print("Nuevo ID categoria: ");
            Long idCategoria = Long.parseLong(scanner.nextLine());

            Categoria categoria =
                    categoriaService.buscarPorId(idCategoria);

            productoService.editarProducto(
                    idProducto,
                    nombre,
                    precio,
                    descripcion,
                    stock,
                    imagen,
                    disponible,
                    categoria);

            System.out.println("Producto modificado correctamente.");

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

   private static void uiEliminarProducto() {
        try {
            uiListarProductos();
            System.out.print("ID del producto: ");
            Long id = Long.parseLong(scanner.nextLine());

            System.out.print("¿Esta seguro que desea eliminar el producto? (S/N): ");
            String confirmacion = scanner.nextLine();

            if (confirmacion.equalsIgnoreCase("S")) {
                productoService.eliminarProducto(id);        
                System.out.println("Producto eliminado correctamente.");
            } else {
                System.out.println("Eliminacion cancelada por el usuario.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     }
   
    private static void menuPedidos() {
        
        int opcion;
        do {
            System.out.println("\n=== MENU PEDIDOS ===");
            System.out.println("1. Listar pedidos");
            System.out.println("2. Crear pedido");
            System.out.println("3. Actualizar Estado/Forma de Pago");
            System.out.println("4. Eliminar pedido");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1: uiListarPedidos(); break;
                    case 2: uiCrearPedido(); break;
                    case 3: uiActualizarPedido(); break;
                    case 4: uiEliminarPedido(); break;
                    case 0: break;
                    default: System.out.println("Opcion invalida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un numero valido.");
                opcion = -1;
            }
        } while (opcion != 0);
    }

    private static void uiListarPedidos() {
        List<Pedido> lista = pedidoService.listarPedidos();
        if (lista.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
        } else {
            System.out.println("\n--- LISTADO DE PEDIDOS ---");
            for (Pedido p : lista) {
                System.out.println(p);
            }
        }
    }

    private static void uiCrearPedido() {
        try {
            System.out.print("ID del Usuario: ");
            Long idUsuario = Long.parseLong(scanner.nextLine());

            Usuario usuario = usuarioService.findUsuarioById(idUsuario); 

            System.out.println("Seleccione Forma de Pago (1.EFECTIVO, 2.TARJETA, 3.TRANSFERENCIA): ");
            int opcionPago = Integer.parseInt(scanner.nextLine());
            FormaPago formaPago = FormaPago.EFECTIVO;
            if (opcionPago == 2) formaPago = FormaPago.TARJETA;
            if (opcionPago == 3) formaPago = FormaPago.TRANSFERENCIA;

            List<Producto> productosCarrito = new ArrayList<>();
            List<Integer> cantidadesCarrito = new ArrayList<>();
            String continuar = "s";

            uiListarProductos(); 

            while (continuar.equalsIgnoreCase("s")) {
                System.out.print("\nID del Producto a agregar: ");
                Long idProd = Long.parseLong(scanner.nextLine());
                Producto producto = productoService.buscarPorId(idProd); 

                System.out.print("Cantidad: ");
                int cantidad = Integer.parseInt(scanner.nextLine());

                productosCarrito.add(producto);
                cantidadesCarrito.add(cantidad);

                System.out.print("¿Agregar otro producto? (s/n): ");
                continuar = scanner.nextLine();
            }

            
            Pedido pedido = pedidoService.crearPedido(usuario, formaPago, productosCarrito, cantidadesCarrito);
            System.out.println("\n¡Pedido creado con éxito! ID: " + pedido.getId());
            System.out.println("Total a pagar: $" + pedido.getTotal());

        } catch (Exception e) {
            System.out.println("\n>>> SE CANCELÓ EL PEDIDO: " + e.getMessage());
        }
    }

    private static void uiActualizarPedido() {
        try {
            System.out.print("ID del Pedido: ");
            Long id = Long.parseLong(scanner.nextLine());

            System.out.println("Nuevo Estado (1.PENDIENTE, 2.CONFIRMADO, 3.TERMINADO, 4.CANCELADO): ");
            int opEstado = Integer.parseInt(scanner.nextLine());
            Estado estado = switch (opEstado) {
                case 1 -> Estado.PENDIENTE;
                case 2 -> Estado.CONFIRMADO;
                case 3 -> Estado.TERMINADO;
                case 4 -> Estado.CANCELADO;
                default -> null;
            };

            System.out.println("Nueva Forma Pago (1.EFECTIVO, 2.TARJETA, 3.TRANSFERENCIA): ");
            int opPago = Integer.parseInt(scanner.nextLine());
            FormaPago pago = switch (opPago) {
                case 1 -> FormaPago.EFECTIVO;
                case 2 -> FormaPago.TARJETA;
                case 3 -> FormaPago.TRANSFERENCIA;
                default -> null;
            };

            pedidoService.actualizarEstadoYPago(id, estado, pago);
            System.out.println("Pedido actualizado correctamente.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

   private static void uiEliminarPedido() {
        try {
            System.out.print("ID del pedido a eliminar: ");
            Long id = Long.parseLong(scanner.nextLine());

            System.out.print("¿Esta seguro que desea eliminar el pedido? (S/N): ");
            String confirmacion = scanner.nextLine();

            if (confirmacion.equalsIgnoreCase("S")) {
                pedidoService.eliminarPedido(id);
                System.out.println("Pedido eliminado correctamente.");
            } else {
                System.out.println("Eliminacion cancelada por el usuario.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        }
   private static void menuUsuarios() {
        int opcion;
        do {            
            System.out.println("\n=== MENU USUARIOS ===");
            System.out.println("1. Crear usuario");
            System.out.println("2. Listar usuarios");
            System.out.println("3. Buscar usuario");
            System.out.println("4. Editar usuario");
            System.out.println("5. Eliminar usuario");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                
                switch (opcion) {
                    case 1:
                        try {
                            System.out.print("Nombre: ");
                            String nombre = scanner.nextLine();
                            System.out.print("Apellido: ");
                            String apellido = scanner.nextLine();
                            System.out.print("Mail: ");
                            String mail = scanner.nextLine();
                            System.out.print("Celular: ");
                            String celular = scanner.nextLine();
                            System.out.print("Contraseña (mínimo 8 caracteres): ");
                            String pass = scanner.nextLine();
                            usuarioService.crearUsuario(nombre, apellido, mail, celular, pass);
                            System.out.println("Usuario creado correctamente.");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 2:
                        usuarioService.listarUsuarios();
                        break;
                    case 3:
                        try {
                            System.out.print("Ingrese ID: ");
                            long id = Long.parseLong(scanner.nextLine());
                            Usuario usuario = usuarioService.findUsuarioById(id); // Asegurate de que el método en UsuarioService se llame así
                            System.out.println(usuario);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 4:
                        try {
                            System.out.print("ID usuario: ");
                            long id = Long.parseLong(scanner.nextLine());
                            System.out.print("Nuevo nombre: ");
                            String nombre = scanner.nextLine();
                            System.out.print("Nuevo apellido: ");
                            String apellido = scanner.nextLine();
                            System.out.print("Nuevo mail: ");
                            String mail = scanner.nextLine();
                            System.out.print("Nuevo celular: ");
                            String celular = scanner.nextLine();
                            usuarioService.editarUsuario(id, nombre, apellido, mail, celular);
                            System.out.println("Usuario actualizado.");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 5:
                        try {
                            System.out.print("ID usuario: ");
                            long id = Long.parseLong(scanner.nextLine());
                            
                            System.out.print("¿Esta seguro que desea eliminar el usuario? (S/N): ");
                            String confirmacion = scanner.nextLine();
                            
                            if (confirmacion.equalsIgnoreCase("S")) {
                                usuarioService.eliminarUsuario(id);
                                System.out.println("Usuario eliminado.");
                            } else {
                                System.out.println("Eliminacion cancelada por el usuario.");
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un numero valido.");
                opcion = -1;
            }

        } while (opcion != 0);
    }
}

