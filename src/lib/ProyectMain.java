//Java imports
package lib;
import java.util.LinkedList;
import java.util.Scanner;

//Proyect imports
import lib.automatons.AF2P;

public class ProyectMain {
    public static void main(String[] args){
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
                        System.out.println("Lo lamentamos, de momento este automata no esta disponible.");
                        break;
                    case 2:
                        System.out.println("Lo lamentamos, de momento este automata no esta disponible.");
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
                        String string, path, fileName;
                        
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
    }
}
