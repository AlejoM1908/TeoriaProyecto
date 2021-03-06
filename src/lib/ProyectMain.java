//Java imports
package lib;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

//Proyect imports
import lib.automatons.AF2P;
import lib.automatons.AFD;
import lib.automatons.AFPD;
import lib.automatons.AFPN;

public class ProyectMain {
    public static void main(String[] args){
        String string, path, fileName;
        
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Bienvenido al sistema de Automatas");
            while (true) {
                System.out.println("Porfavor seleccione que Automata desea usar:");
                System.out.println("1. Automata Finito Determinista");
                System.out.println("2. Automata Finito con Pila Determinista");
                System.out.println("3. Automata Finito con Pila no Determinista");
                System.out.println("4. Automata Finito con Dos Pilas");
                System.out.println("5. Máquina de turing - Modelo estándar");
                System.out.println("6. Máquina de turing - Modelo con cinta dividida en pistas");
                System.out.println("7. Máquina de turing - Modelo con multiples cintas");
                System.out.println("8. Máquina de turing - No determinista");
                System.out.println("9. finalizar ejecución");
                
                int selection = scanner.nextInt();
                scanner.nextLine();
                
                switch(selection){
                    case 1:
                        System.out.println("Por favor ingrese la ubicación del documento de inicialización");
                        AFD afd = new AFD(scanner.next());
                        System.out.println("Se cargo el automata con la siguiente configuración:");
                        System.out.println(afd.toString());
                        System.out.println("Que tipo de procesamiento desea ejecutar sobre el automata?");
                        System.out.println("1. Procesar y retornar solo si es aceptada o no");
                        System.out.println("2. Procesar cadena con detalles retornando si es aceptada o no");
                        System.out.println("3. Procear una lista de cadenas detalladamente");
                        selection = scanner.nextInt();
                        scanner.nextLine();
                        
                        switch(selection){
                            case 1:
                                System.out.println("Ingrese la cadena que desea procesar");
                                string = scanner.nextLine();
                                afd.processString(string, false);
                                
                                
                                break;
                            case 2:
                                System.out.println("Ingrese la cadena que desea procesar");
                                string = scanner.nextLine();
                                afd.processString(string, true);
                                
                                break;
                            case 3:
                                System.out.println("Por favor ingrese las cadenas que desea procesar");
                                System.out.println("Cuando desee parar de agregar cadenas, coloque \"stop**\"");
                                LinkedList<String> strings = new LinkedList<>();
                                boolean stop = false;
                                
                                while (!stop){
                                    string = scanner.nextLine();
                                    
                                    if (string.compareTo("stop**") == 0){
                                        stop = true;
                                    }
                                    else
                                        strings.add(string);
                                }
                                
                                System.out.println("Ingrese el nombre del archivo en el que desea guardar");
                                fileName = scanner.nextLine();

                                try {
                                    afd.processStringList(strings, fileName, true);
                                } catch (IOException ex) {
                                    Logger.getLogger(ProyectMain.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                break;
                            default:
                        }

                        break;
                    case 2:
                        System.out.println("Por favor ingrese la ubicación del documento de inicialización");
                        AFPD afpd = new AFPD(scanner.next());
                        System.out.println("Se cargo el automata con la siguiente configuración:");
                        System.out.println(afpd.toString());
                        System.out.println("Que tipo de procesamiento desea ejecutar sobre el automata?");
                        System.out.println("1. Procesar y retornar solo si es aceptada o no");
                        System.out.println("2. Procesar cadena con detalles retornando si es aceptada o no");
                        System.out.println("3. Procear una lista de cadenas detalladamente");
                        System.out.println("4. Producto Cartesiano AFD con un AFPD");
                        selection = scanner.nextInt();
                        scanner.nextLine();

                        switch (selection) {
                            case 1:
                                System.out.println("Ingrese la cadena que desea procesar");
                                string = scanner.nextLine();
                                boolean result = afpd.processString(string);

                                if (result) {
                                    System.out.println("La cadena es acepatada");
                                } else {
                                    System.out.println("La cadena no es aceptada");
                                }

                                break;
                            case 2:
                                System.out.println("Ingrese la cadena que desea procesar");
                                string = scanner.nextLine();
                                afpd.processStringWithDetails(string);

                                break;
                            case 3:
                                System.out.println("Por favor ingrese las cadenas que desea procesar");
                                System.out.println("Cuando desee parar de agregar cadenas, coloque \"stop**\"");
                                LinkedList<String> strings = new LinkedList<>();
                                boolean stop = false;

                                while (!stop) {
                                    string = scanner.nextLine();

                                    if (string.compareTo("stop**") == 0) {
                                        stop = true;
                                    } else {
                                        strings.add(string);
                                    }
                                }

                                System.out.println("Ingrese el nombre del archivo en el que desea guardar");
                                fileName = scanner.nextLine();

                                try {
                                    afpd.processStringList(strings, fileName, true);
                                } catch (IOException ex) {
                                    System.out.println(Arrays.toString(ex.getStackTrace()));
                                    Logger.getLogger(ProyectMain.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                break;
                            case 4:
                                System.out.println("Por favor ingrese el nombre del archivo para el AUTOMATA FINITO DETERMINISTA");
                                AFD cartesianAFD = new AFD(scanner.nextLine());
                                System.out.println("Por favor ingrese el nombre del archivo para el AUTOMATA FINITO CON PILA DETERMINISTA");
                                AFPD cartesianAFPD = new AFPD(scanner.nextLine());
                                AFPD cartesianProduct = cartesianAFPD.cartesianProductAFD(cartesianAFD);
                                boolean run;
                                if (cartesianProduct == null) {
                                    run = false;
                                } else {
                                    System.out.println("Producto cartesiano realizado con exito, que desea hacer");
                                    run = true;
                                }
                                int continuar;
                                while (run) {
                                    System.out.println(cartesianProduct.toString());
                                    System.out.println("1. Procesar y retornar solo si es aceptada o no");
                                    System.out.println("2. Procesar cadena con detalles retornando si es aceptada o no");
                                    System.out.println("3. Procear una lista de cadenas detalladamente");
                                    selection = scanner.nextInt();
                                    scanner.nextLine();

                                    switch (selection) {
                                        case 1:
                                            System.out.println("Ingrese la cadena que desea procesar");
                                            string = scanner.nextLine();
                                            boolean resultCartesian = cartesianProduct.processString(string);
                                            if (resultCartesian) {
                                                System.out.println("La cadena es acepatada");
                                            } else {
                                                System.out.println("La cadena no es aceptada");
                                            }

                                            break;
                                        case 2:
                                            System.out.println("Ingrese la cadena que desea procesar");
                                            string = scanner.nextLine();
                                            cartesianProduct.processStringWithDetails(string);
                                            break;
                                        case 3:
                                            System.out.println("Por favor ingrese las cadenas que desea procesar");
                                            System.out.println("Cuando desee parar de agregar cadenas, coloque \"stop**\"");
                                            LinkedList<String> stringsCartesian = new LinkedList<>();
                                            boolean stopCartesian = false;

                                            while (!stopCartesian) {
                                                string = scanner.nextLine();

                                                if (string.compareTo("stop**") == 0) {
                                                    stopCartesian = true;
                                                } else {
                                                    stringsCartesian.add(string);
                                                }
                                            }

                                            System.out.println("Ingrese el nombre del archivo en el que desea guardar");
                                            fileName = scanner.nextLine();

                                            try {
                                                cartesianProduct.processStringList(stringsCartesian, fileName, true);
                                            } catch (IOException ex) {
                                                System.out.println(Arrays.toString(ex.getStackTrace()));
                                                Logger.getLogger(ProyectMain.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                            break;
                                        default:
                                    }

                                    System.out.println("Desea procesar mas cadenas?");
                                    System.out.println("Ingrese 1 para continuar, 0 para finalizar");
                                    continuar=scanner.nextInt();
                                    scanner.nextLine();
                                    
                                    if(continuar==0)
                                        break;
                                    
                                }
                            default:
                        }
 
                        break;
                    case 3:
                        System.out.println("Por favor ingrese la ubicación del documento de inicialización");
                        AFPN afpn = new AFPN(scanner.next());
                        System.out.println("Se cargo el automata con la siguiente configuración:");
                        System.out.println(afpn.toString());
                        System.out.println("Que tipo de procesamiento desea ejecutar sobre el automata?");
                        System.out.println("1. Procesar y retornar solo si es aceptada o no");
                        System.out.println("2. Procesar cadena con detalles retornando si es aceptada o no");
                        System.out.println("3. Procesar cadena y obtener todos los posibles procesamientos");
                        System.out.println("4. Procear una lista de cadenas detalladamente");
                        selection = scanner.nextInt();
                        scanner.nextLine();
                        
                        
                        switch(selection){
                            case 1:
                                System.out.println("Ingrese la cadena que desea procesar");
                                string = scanner.next();
                                boolean acceptance = afpn.isAccepted(string);
                                
                                if (acceptance)
                                    System.out.println("La cadena es acepatada");
                                else
                                    System.out.println("La cadena no es aceptada");
                                
                                break;
                            case 2:
                                System.out.println("Ingrese la cadena que desea procesar");
                                string = scanner.next();
                                afpn.detailedProcessig(string, true);
                                break;
                            case 3:
                                System.out.println("Ingrese la cadena que desea procesar");
                                string = scanner.nextLine();
                                System.out.println("");
                                
                                System.out.println("Ingrese la ruta de la carpeta donde desea guardar los procesamientos");
                                path = scanner.nextLine();
                                
                                System.out.println("Ingrese el nombre de los archivos que desea");
                                fileName = scanner.nextLine();
                                
                                afpn.completedProcedure(string, path, fileName, true);
                                
                                break;
                            case 4:
                                System.out.println("Por favor ingrese las cadenas que desea procesar");
                                System.out.println("Cuando desee parar de agregar cadenas, coloque \"stop**\"");
                                LinkedList<String> strings = new LinkedList<>();
                                boolean stop = false;
                                
                                while (!stop){
                                    string = scanner.nextLine();
                                    
                                    if (string.compareTo("stop**") == 0){
                                        stop = true;
                                    }
                                    else
                                        strings.add(string);
                                }
                                
                                System.out.println("Ingrese la ruta de la carpeta donde desea guardar los procesamientos");
                                path = scanner.nextLine();
                                
                                System.out.println("Ingrese el nombre de los archivos que desea");
                                fileName = scanner.nextLine();
                                afpn.processList(strings, path, fileName, true);
                                
                                break;
                            default:
                        }
                        
                        break;
                    case 4:
                        System.out.println("Por favor ingrese la ubicación del documento de inicialización");
                        AF2P af2p = new AF2P(scanner.nextLine());
                        System.out.println("Se cargo el automata con la siguiente configuración:");
                        System.out.println(af2p.toString());
                        System.out.println("Que tipo de procesamiento desea ejecutar sobre el automata?");
                        System.out.println("1. Procesar y retornar solo si es aceptada o no");
                        System.out.println("2. Procesar cadena con detalles retornando si es aceptada o no");
                        System.out.println("3. Procesar cadena y obtener todos los posibles procesamientos");
                        System.out.println("4. Procear una lista de cadenas detalladamente");
                        selection = scanner.nextInt();
                        scanner.nextLine();
                        
                        
                        switch(selection){
                            case 1:
                                System.out.println("Ingrese la cadena que desea procesar");
                                string = scanner.nextLine();
                                boolean acceptance = af2p.isAccepted(string);
                                
                                if (acceptance)
                                    System.out.println("La cadena es acepatada");
                                else
                                    System.out.println("La cadena no es aceptada");
                                
                                break;
                            case 2:
                                System.out.println("Ingrese la cadena que desea procesar");
                                string = scanner.nextLine();
                                af2p.detailedProcessing(string, true);
                                acceptance = af2p.isAccepted(string);
                                
                                if (acceptance)
                                    System.out.println("La cadena es acepatada");
                                else
                                    System.out.println("La cadena no es aceptada");
                                System.out.println("");

                                System.out.println("Ingrese la ruta de la carpeta donde desea guardar los procesamientos");
                                path = scanner.nextLine();
                                
                                System.out.println("Ingrese el nombre de los archivos que desea");
                                fileName = scanner.nextLine();
                                
                                af2p.completeDetailedProcessing(string, path, fileName, true);
                                
                                break;
                            case 3:
                                System.out.println("Ingrese la cadena que desea procesar");
                                string = scanner.nextLine();
                                
                                System.out.println("Ingrese la ruta de la carpeta donde desea guardar los procesamientos");
                                path = scanner.nextLine();
                                
                                System.out.println("Ingrese el nombre de los archivos que desea");
                                fileName = scanner.nextLine();
                                
                                af2p.completeDetailedProcessing(string, path, fileName, true);
                                
                                break;
                            case 4:
                                System.out.println("Por favor ingrese las cadenas que desea procesar");
                                System.out.println("Cuando desee parar de agregar cadenas, coloque \"stop**\"");
                                LinkedList<String> strings = new LinkedList<>();
                                boolean stop = false;
                                
                                while (!stop){
                                    string = scanner.nextLine();
                                    
                                    if (string.compareTo("stop**") == 0){
                                        stop = true;
                                    }
                                    else
                                        strings.add(string);
                                }
                                
                                System.out.println("Ingrese la ruta de la carpeta donde desea guardar los procesamientos");
                                path = scanner.nextLine();
                                
                                System.out.println("Ingrese el nombre de los archivos que desea");
                                fileName = scanner.nextLine();
                                
                                af2p.processStringList(strings, path, fileName, true);
                                
                                break;
                            default:
                        }
                        
                        break;
                    case 5:
                        System.out.println("Lo lamentamos, de momento este automata no esta disponible.");
                        break;
                    case 6:
                        System.out.println("Lo lamentamos, de momento este automata no esta disponible.");
                        break;
                    case 7:
                        System.out.println("Lo lamentamos, de momento este automata no esta disponible.");
                        break;
                    case 8:
                        System.out.println("Lo lamentamos, de momento este automata no esta disponible.");
                        break;
                    case 9: 
                        return;
                    default:
                        System.out.println("La selección escogida no fue reconocida, intente nuevamente!");
                        break;
                }
                
                System.out.println("Desea finalizar el programa? (s/n)");
                if (scanner.nextLine().compareTo("s") == 0)
                    return;
            }
        }
    }
}

