import java.io.*;
import java.util.*;

class Vehicle implements Serializable {
    static Scanner input = new Scanner(System.in);
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    String ownerName;
    int numVehicles;
    String type;
    String energyUsed;
    String numberPlate;
    double mileage;

    void insert(FileWriter writer) {
        try {
            System.out.println("\nEnter Owner Name: ");
            ownerName = br.readLine();
            writer.write("\n\nOwner Name: " + ownerName);

            System.out.println("Enter Vehicle Type (e.g., car, bike): ");
            type = br.readLine();
            writer.write("\nVehicle Type: " + type);

            System.out.println("Enter Energy Used (electricity, petrol, diesel): ");
            energyUsed = br.readLine();
            writer.write("\nEnergy Used: " + energyUsed);

            System.out.println("Enter Number Plate: ");
            numberPlate = br.readLine();
            writer.write("\nNumber Plate: " + numberPlate);

            System.out.println("Enter Mileage: ");
            mileage = Double.parseDouble(br.readLine());
            writer.write("\nMileage: " + mileage);

            double carbonFootprint = calculateCarbonFootprint();
            writer.write("\nCarbon Footprint for Vehicle " + ": " + carbonFootprint + " kg CO2");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void deleteByOwnerName(String ownerNameToDelete) {
        try {
            File inputFile = new File("CarbonFootprintOutput.txt");
            File tempFile = new File("TempOutput.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String lineToRemove = "Owner Name: " + ownerNameToDelete;
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.equals(lineToRemove)) {
                    for (int i = 0; i < 6; i++) {
                        reader.readLine();
                    }
                } else {
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
            }

            writer.close();
            reader.close();

            if (inputFile.delete()) {
                tempFile.renameTo(new File("CarbonFootprintOutput.txt"));
                System.out.println("Record deleted successfully.");
            } else {
                System.out.println("Error deleting record.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void searchByOwnerName(String ownerNameToSearch) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("CarbonFootprintOutput.txt"));
            String currentLine;

            boolean ownerFound = false;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.contains("Owner Name: " + ownerNameToSearch)) {
                    ownerFound = true;
                    System.out.println("Details for " + ownerNameToSearch + ":");
                    for (int i = 0; i < 6; i++) {
                        // Display details for the entire vehicle (7 lines)
                        System.out.println(reader.readLine());
                    }
                }
            }

            if (!ownerFound) {
                System.out.println("No record found for " + ownerNameToSearch);
            }

            reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void displayAllDetails() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("CarbonFootprintOutput.txt"));
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                System.out.println(currentLine);
            }

            reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    double calculateCarbonFootprint() {
        double emissionFactor;
        switch (energyUsed.toLowerCase()) {
            case "electricity":
                emissionFactor = 0.21; // kg CO2 per kWh (example value)
                break;
            case "petrol":
                emissionFactor = 2.3; // kg CO2 per liter (example value)
                break;
            case "diesel":
                emissionFactor = 2.7; // kg CO2 per liter (example value)
                break;
            default:
                emissionFactor = 0;
                break;
        }
        return mileage * emissionFactor;
    }
}

public class Carbon_Footprint extends Vehicle {
    public static void main(String args[]) {
        try {
            FileWriter writer = new FileWriter("CarbonFootprintOutput.txt", true);

            while (true) {
                System.out.println("\nEnter your choice: ");
                System.out.println("1: Insert Vehicle Details.");
                System.out.println("2: Delete Vehicle Details by Owners Name.");
                System.out.println("3: Search Vehicle Details by Owner Name.");
                System.out.println("4: Display All Details.");
                System.out.println("5: Exit.\n");

                int choice = input.nextInt();
                Vehicle v = new Vehicle();

                switch (choice) {
                    case 1:
                        v.insert(writer);
                        break;
                    case 2:
                        System.out.println("Enter Owner Name to delete: ");
                        String ownerNameToDelete = input.next();
                        v.deleteByOwnerName(ownerNameToDelete);
                        break;
                    case 3:
                        System.out.println("Enter Owner Name to search: ");
                        String ownerNameToSearch = input.next();
                        v.searchByOwnerName(ownerNameToSearch);
                        break;
                    case 4:
                        v.displayAllDetails();
                        break;
                    case 5:
                        writer.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid Entry!!");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
