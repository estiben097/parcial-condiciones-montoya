import java.util.Scanner;

public class TarificadorTaxiYopal {

    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        
        System.out.println("=== Bienvenido al Tarificador de TaxiYopal ===\n");
        
        // ==== VALIDACIONES ====
        
        System.out.print("Ingrese tipo de vehículo (1-3): ");
        int v = sc.nextInt();
        if (v < 1 || v > 3) {
            System.out.println("Tipo de vehículo no válido");
            sc.close();
            return;
        }
        
        System.out.print("Ingrese distancia en kilómetros: ");
        double km = sc.nextDouble();
        if (km <= 0) {
            System.out.println("Distancia inválida");
            sc.close();
            return;
        }
        
        System.out.print("Ingrese hora de inicio (0-23): ");
        int hora = sc.nextInt();
        if (hora < 0 || hora > 23) {
            System.out.println("Hora inválida");
            sc.close();
            return;
        }
        
        sc.nextLine(); // Limpiar buffer
        
        System.out.print("¿Es domingo o festivo? (S/N): ");
        String d = sc.nextLine().strip().toUpperCase();
        if (!d.equals("S") && !d.equals("N")) {
            System.out.println("Respuesta S/N inválida");
            sc.close();
            return;
        }
        
        System.out.print("¿Hay lluvia fuerte? (S/N): ");
        String l = sc.nextLine().strip().toUpperCase();
        if (!l.equals("S") && !l.equals("N")) {
            System.out.println("Respuesta S/N inválida");
            sc.close();
            return;
        }
        
        System.out.print("¿Es viaje rural? (S/N): ");
        String r = sc.nextLine().strip().toUpperCase();
        if (!r.equals("S") && !r.equals("N")) {
            System.out.println("Respuesta S/N inválida");
            sc.close();
            return;
        }
        
        System.out.print("Ingrese tipo de pasajero (1-4): ");
        int p = sc.nextInt();
        if (p < 1 || p > 4) {
            System.out.println("Tipo de pasajero no válido");
            sc.close();
            return;
        }
        
        System.out.print("Ingrese edad del pasajero: ");
        int edad = sc.nextInt();
        if (edad < 0 || edad > 120) {
            System.out.println("Edad fuera de rango");
            sc.close();
            return;
        }
        
        // Validación adicional Adulto Mayor
        if (p == 3 && edad < 60) {
            System.out.println("Inconsistencia: edad no corresponde a adulto mayor");
            p = 4;
        }
        
        // ======= REGLA 1 - TARIFA BASE =======
        
        double tarifaKm = 0;
        double tarifaMinima = 0;
        
        if (v == 1) {           // Motocarro
            tarifaKm = 1000;
            tarifaMinima = 5000;
        } else if (v == 2) {    // Automóvil
            tarifaKm = 2000;
            tarifaMinima = 8000;
        } else {                // Camioneta 4x4
            tarifaKm = 2500;
            tarifaMinima = 12000;
        }
        
        double subtotal = km * tarifaKm;
        boolean aplicoMinima = false;
        
        if (subtotal < tarifaMinima) {
            subtotal = tarifaMinima;
            aplicoMinima = true;
        }
        
        // =========== REGLA 2 - RECARGOS =========
        
        double recargo = 0.0;
        
        // Nocturno
        if (hora >= 22 || hora < 5) {
            recargo = recargo + 0.20;
        }
        
        // Domingo o festivo
        if (d.equals("S")) {
            recargo = recargo + 0.15;
        }
        
        // Lluvia fuerte
        if (l.equals("S")) {
            recargo = recargo + 0.10;
        }
        
        // Rural
        if (r.equals("S")) {
            recargo = recargo + 0.25;
        }
        
        double valorConRecargos = subtotal * (1 + recargo);
        
        // ============= REGLA 3 - DESCUENTO ================
        
        double descuento = 0.0;
        String tipoPasajero = "";
        
        if (p == 1) {
            descuento = 0.10;
            tipoPasajero = "Pasajero Frecuente";
        } else if (p == 2) {
            descuento = 0.08;
            tipoPasajero = "Estudiante";
        } else if (p == 3) {
            descuento = 0.12;
            tipoPasajero = "Adulto Mayor";
        } else {
            descuento = 0.0;
            tipoPasajero = "Pasajero Ocasional";
        }
        
        double totalFinal = valorConRecargos * (1 - descuento);
        
        // =========== REGLA 4 - TARIFA SOLIDARIA ============
        
        boolean aplicoSolidaria = false;
        
        if (r.equals("N")) {  // Viaje urbano
            if (totalFinal < tarifaMinima) {
                totalFinal = tarifaMinima;
                aplicoSolidaria = true;
            }
        }
        
        // ===================== RECIBO FINAL =====================
        
        System.out.println("\n" + "=".repeat(55));
        System.out.println("              RECIBO DE VIAJE - TAXIYOPAL");
        System.out.println("=".repeat(55));
        System.out.println("Vehículo: " + v + "          Kilómetros: " + km + " km");
        System.out.printf("Subtotal base:         $%,.0f%n", subtotal);
        
        if (aplicoMinima) {
            System.out.println("→ Se aplicó tarifa mínima");
        }
        
        System.out.printf("Recargos aplicados:    %.0f%%%n", recargo * 100);
        System.out.printf("Valor con recargos:    $%,.0f%n", valorConRecargos);
        System.out.println("Descuento (" + tipoPasajero + "): " + (descuento * 100) + "%");
        System.out.printf("TOTAL A PAGAR:         $%,.0f%n", totalFinal);
        
        if (aplicoSolidaria) {
            System.out.println("→ Se aplicó tarifa solidaria mínima");
        }
        
        System.out.println("=".repeat(55));
        
        sc.close();
    }
}