import java.util.*;
import com.github.packageurl.PackageURL;
import com.github.packageurl.MalformedPackageURLException;

/*
Generates a simple Purl (Package URL) for dependency-track 
*/
class PurlGenerator {

	private Scanner scanner;
	private Package myPackage;
	private StringBuilder purlBuilder;

	public static void main(String[] args) {
		PurlGenerator pg = new PurlGenerator();
		pg.run();
	}

	public void run() {
		setUp();
		handlePackageType();
		handlePackageName();
		handlePackageVersion();
		generatePurl();
		//verifyPurl(purlBuilder.toString());
	}

	//Package Type Functions

	//Package type variables
	private final int MAVEN_TYPE = 1;
	private final int NPM_TYPE = 2;
	private final int GEM_TYPE = 3;
	private final String MAVEN = "maven";
	private final String NPM = "npm";
	private final String GEM = "gem";
	private boolean isInputTypeValid = false;

	private void handlePackageType() {
		inputPackageType();
		processPackageTypeInputValidation();
		validatePackageTypeInput();
	}

	private void printPackageTypeInputMenu() {
		System.out.println("====================Package URL Generator Lulzxc======================");
		System.out.println("Enter the package type (1/2/3): ");
		System.out.println("1) Maven");
		System.out.println("2) NPM");
		System.out.println("3) Gem");
		System.out.print("Your Input: ");
	}

	private void inputPackageType() {
		printPackageTypeInputMenu();
		try {
			processPackageType(Integer.parseInt(scanner.nextLine()));
		} catch (NumberFormatException nfe) {
			System.out.println("Invalid input! Please enter a number.");
		}
		System.out.println();
	}

	private void processPackageType(int inputType) {
		if (inputType == MAVEN_TYPE) {
			this.myPackage.setPackageType(MAVEN);
		} else if (inputType == NPM_TYPE) {
			this.myPackage.setPackageType(NPM);
		} else if (inputType == GEM_TYPE) { 
			this.myPackage.setPackageType(GEM);
		} else {
			System.out.println("Invalid number! Please choose between 1/2/3");
		}
	}

	private void processPackageTypeInputValidation() {
		if (this.myPackage.getPackageType() == null) {
			this.isInputTypeValid = false;
		} else if (isValidPackageType()) {
			this.isInputTypeValid = true;
		} else {
			this.isInputTypeValid = false;
		}
	}

	private boolean isValidPackageType() {
		return this.myPackage.getPackageType().equals(MAVEN) ||
			this.myPackage.getPackageType().equals(NPM) ||
			this.myPackage.getPackageType().equals(GEM);
	}

	private void validatePackageTypeInput() {
		while (!isInputTypeValid) {
			handlePackageType();
		}
	}

	// Package Name Functions

	private void handlePackageName() {
		inputPackageName();
	}

	private void printPackageNameInputMenu() {
		System.out.print("Enter the package name: ");
	}

	private void inputPackageName() {
		printPackageNameInputMenu();
		processPackageName(scanner.nextLine());
	}

	private void processPackageName(String inputName) {
		this.myPackage.setPackageName(inputName);
	}

	//Package Version Functions

	private void handlePackageVersion() {
		inputPackageVersion();
	}

	private void printPackageVersionInputMenu() {
		System.out.print("Enter the package version: ");
	}

	private void inputPackageVersion() {
		printPackageVersionInputMenu();
		processPackageVersion(scanner.nextLine());
	}

	private void processPackageVersion(String inputVersion) {
		this.myPackage.setPackageVersion(inputVersion);
	}

	//Purl Generation Functions

	//Purl component variables
	private final String SCHEME = "pkg";
	private final String SCHEME_DELIMETER = ":";
	private final String TYPE_DELIMETER = "/";
	private final String VERSION_DELIMETER = "@";

	private void generatePurl() {
		purlBuilder = new StringBuilder();
		switch (myPackage.getPackageType()) {
			case MAVEN:
				generateMavenPurl();
				break;
			case NPM:
				generateNpmPurl();
				break;
			case GEM:
				generateGemPurl();
				break;
		}
		printPurl();
	}

	private void printPurl() {
		System.out.println();
		System.out.println("*****************Your Generated PURL*********************");
		System.out.println(purlBuilder.toString());
		System.out.println();
		System.out.println("Note that for maven and gem you might still need to input the group/vendor as well.");
	}

	private void generateMavenPurl() {
		purlBuilder.append(SCHEME);
		purlBuilder.append(SCHEME_DELIMETER);
		purlBuilder.append(MAVEN);
		purlBuilder.append(TYPE_DELIMETER);
		purlBuilder.append(myPackage.getPackageName());
		purlBuilder.append(VERSION_DELIMETER);
		purlBuilder.append(myPackage.getPackageVersion());
	}

	private void generateNpmPurl() {
		purlBuilder.append(SCHEME);
		purlBuilder.append(SCHEME_DELIMETER);
		purlBuilder.append(NPM);
		purlBuilder.append(TYPE_DELIMETER);
		purlBuilder.append(myPackage.getPackageName());
		purlBuilder.append(VERSION_DELIMETER);
		purlBuilder.append(myPackage.getPackageVersion());
	}

	private void generateGemPurl() {
		purlBuilder.append(SCHEME);
		purlBuilder.append(SCHEME_DELIMETER);
		purlBuilder.append(GEM);
		purlBuilder.append(TYPE_DELIMETER);
		purlBuilder.append(myPackage.getPackageName());
		purlBuilder.append(VERSION_DELIMETER);
		purlBuilder.append(myPackage.getPackageVersion());
	}

	//Util Functions

	private void setUp() {
		scanner = new Scanner(System.in);
		myPackage = new Package();
		purlBuilder = new StringBuilder();
	}

	private void debug() {
		System.out.println("************Debugging**************");
		System.out.println(myPackage.getPackageType());
		System.out.println(myPackage.getPackageName());
	}

	/*
	private void verifyPurl(String purl) {
		try {
			PackageURL packageUrl = new PackageURL(purl);
		} catch (MalformedPackageURLException mpe) {
			System.out.println("Invalid inputs!");
		}
	}
	*/
}

class Package {
	private String packageName;
	private String packageVersion;
	private String packageType;

	public Package() {
		packageName = "";
		packageVersion = "";
		packageType = "";
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setPackageVersion(String packageVersion) {
		this.packageVersion = packageVersion;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	public String getPackageName() {
		return this.packageName;
	}

	public String getPackageVersion() {
		return this.packageVersion;
	}

	public String getPackageType() {
		return this.packageType;
	}
}