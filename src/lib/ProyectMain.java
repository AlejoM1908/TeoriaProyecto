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

public class ProyectMain {
    public static void main(String[] args){
        String string, path, fileName, shorcutProcess, shorcutAutomaton, absolutePath;
        absolutePath = System.getProperty("user.dir");
        shorcutProcess = "\\resultadosProcesamiento\\";
        shorcutAutomaton = "\\txtTest\\";
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
                
                int selection = scanner.nextInt();
                
                switch(selection){
                    case 1:
                        System.out.println("Por favor ingrese la ubicación del documento de inicialización");
                        AFD afd = new AFD(absolutePath + shorcutAutomaton + scanner.next());
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
                                string = scanner.next();
                                afd.processString(string, false);
                                
                                
                                break;
                            case 2:
                                System.out.println("Ingrese la cadena que desea procesar");
                                string = scanner.next();
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
                        AFPD afpd = new AFPD(absolutePath + shorcutAutomaton + scanner.next());
                        System.out.println("Se cargo el automata con la siguiente configuración:");
                        System.out.println(afpd.toString());
                        System.out.println("Que tipo de procesamiento desea ejecutar sobre el automata?");
                        System.out.println("1. Procesar y retornar solo si es aceptada o no");
                        System.out.println("2. Procesar cadena con detalles retornando si es aceptada o no");
                        System.out.println("3. Procear una lista de cadenas detalladamente");
                        selection = scanner.nextInt();
                        scanner.nextLine();
                        
                        switch(selection){
                            case 1:
                                System.out.println("Ingrese la cadena que desea procesar");
                                string = scanner.next();
                                boolean result = afpd.processString(string);
                                
                                if (result)
                                    System.out.println("La cadena es acepatada");
                                else
                                    System.out.println("La cadena no es aceptada");
                                
                                
                                break;
                            case 2:
                                System.out.println("Ingrese la cadena que desea procesar");
                                string = scanner.next();
                                afpd.processStringWithDetails(string);
                                
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
                                    afpd.processStringList(strings, fileName, true);
                                } catch (IOException ex) {
                                    System.out.println(Arrays.toString(ex.getStackTrace()));
                                    Logger.getLogger(ProyectMain.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                break;
                            default:
                        }
                        
                        break;
                    case 3:
                        System.out.println("Lo lamentamos, de momento este automata no esta disponible.");
                        break;
                    case 4:
                        System.out.println("Por favor ingrese la ubicación del documento de inicialización");
                        AF2P af2p = new AF2P(scanner.next());
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
                                string = scanner.next();
                                boolean acceptance = af2p.isAccepted(string);
                                
                                if (acceptance)
                                    System.out.println("La cadena es acepatada");
                                else
                                    System.out.println("La cadena no es aceptada");
                                
                                break;
                            case 2:
                                System.out.println("Ingrese la cadena que desea procesar");
                                string = scanner.next();
                                af2p.detailedProcessing(string, true);
                                
                                break;
                            case 3:
                                System.out.println("Ingrese la cadena que desea procesar");
                                string = scanner.nextLine();
                                System.out.println("");
                                
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
                    default:
                        System.out.println("La selección escogida no fue reconocida, intente nuevamente!");
                        break;
                }
                
                System.out.println("Desea finalizar el programa? (s/n)");
                if (scanner.nextLine().compareTo("s") == 0)
                    return;
            }
            /*
            AFD prueba = new AFD("C:\\Users\\Armageddon132\\Documents\\NetBeansProjects\\TeoriaProyecto\\txtTest\\AFD.txt");
            System.out.println(prueba.toString());
            prueba.processString("abab", true);
            try {
            prueba.processStringList(Arrays.asList("abab", "baba"), "Test.txt", true);
            } catch (IOException ex) {
            Logger.getLogger(ProyectMain.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        }
        /*
        AFPD test = new AFPD("D:\\Personal\\Documentos\\NetBeansProjects\\TeoriaProyecto\\txtTest\\AFPD2.txt");
        test.processStringWithDetails("aaabbbbbb");
        AFPD testCartesian = test.cartesianProductAFD(prueba);
        testCartesian.processStringWithDetails("aabb");
        //System.out.println("Ayuda");
        /*
        try {
            test.processStringList(Arrays.asList("aaaabb", "abb", "aabb"), "TestAFPD.txt", true);
        } catch (IOException ex) {
            Logger.getLogger(ProyectMain.class.getName()).log(Level.SEVERE, null, ex);
        }*/

    }
}
