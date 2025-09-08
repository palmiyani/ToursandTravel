import dsa.LinkedList;
import dsa.CustomQueue;

public class PackageDetails {

    private String packageID;
    private String packageName;
    private String packageDescription;
    private double price;

    public PackageDetails(String packageID, String packageName, String packageDescription, double price) {
        this.packageID = packageID;
        this.packageName = packageName;
        this.packageDescription = packageDescription;
        this.price = price;
    }

    public String getPackageID() {
        return packageID;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getPackageDescription() {
        return packageDescription;
    }

    public double getPrice() {
        return price;
    }

    private static CustomQueue<PackageDetails> packageQueue = new CustomQueue<>();

    static {
        packageQueue.enqueue(new PackageDetails("PKG001", "Jaipur Tour", "A wonderful tour of Jaipur.", 5000));
        packageQueue.enqueue(new PackageDetails("PKG002", "Goa Tour", "A relaxing beach holiday in Goa.", 10000));
        packageQueue.enqueue(new PackageDetails("PKG003", "Kashmir Tour", "An adventurous trip to Kashmir.", 15000));
    }

    public static void viewPackages() {
        CustomQueue<PackageDetails> tempQueue = new CustomQueue<>();
        while (!packageQueue.isEmpty()) {
            PackageDetails pkg = packageQueue.dequeue();
            System.out.println("Package ID: " + pkg.getPackageID());
            System.out.println("Package Name: " + pkg.getPackageName());
            System.out.println("Package Description: " + pkg.getPackageDescription());
            System.out.println("Price: " + pkg.getPrice());
            System.out.println("-------------------------------");
            tempQueue.enqueue(pkg);
        }
        while (!tempQueue.isEmpty()) {
            packageQueue.enqueue(tempQueue.dequeue());
        }
    }

    public static PackageDetails getPackageById(String packageID) {
        CustomQueue<PackageDetails> tempQueue = new CustomQueue<>();
        PackageDetails foundPackage = null;
        
        while (!packageQueue.isEmpty()) {
            PackageDetails pkg = packageQueue.dequeue();
            if (pkg.getPackageID().equals(packageID)) {
                foundPackage = pkg;
            }
            tempQueue.enqueue(pkg);
        }
        
       
        while (!tempQueue.isEmpty()) {
            packageQueue.enqueue(tempQueue.dequeue());
        }
        
        return foundPackage;
    }
    
}
