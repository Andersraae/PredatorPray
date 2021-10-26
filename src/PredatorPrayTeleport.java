import java.awt.Point;

public class PredatorPrayTeleport {

    public static void main(String[] args){
        runSimulation(50, 2, 20);
    }

    public static void runSimulation(int n, int s, int t){
        System.out.println(String.format("n=%d s=%d t=%d", n, s, t));
        Point prey = new Point((int) (Math.random() * n),(int) (Math.random() * n));
        //Generer to tilfældige tal, som kan blive alle tal fra 0 til n-1.
        Point predator = new Point((int) (Math.random() * n),(int) (Math.random() * n));
        int lastteleport = t + 1;

        for (int i = t; i >= 0; i--){ // Vi sætter koden i en for-loop, så den kører t gange
            if(n <= 0 || s < 2 || t < 0){ //Vi laver en if-loop, der tester, om parametrene er indenfor definitionsmængden
                System.out.println("Illegal Parameters!");
                break;
            }

            System.out.println(String.format("[%d;%d] [%d;%d]", prey.x, prey.y, predator.x, predator.y));
            if (predator.x == prey.x && predator.y == prey.y){ //Hvis predator fanger prey
                System.out.print("Catch!");
                break;
            }

            ////PREY
            if (prey.x %s == 0 && prey.y %s == 0 && i != lastteleport - 1){
                lastteleport = i;
                prey.setLocation(getRandomCoord(0, n-1) ,getRandomCoord(0, n-1));
                //Teleporterer prey, hvis begge preys koordinater er dividerbare med s (udover i startpositionen)
            } else {
                int xdirection = getRandomCoord(-s, s);
                //For prey definerer vi et udtryk for dens bevægelse
                prey.x += xdirection; //preys nye position bliver fastlagt

                int ydirection = getRandomCoord(-s, s); // det samme udføres for preys bevægelse i y-retningen
                prey.y += ydirection;

                prey.setLocation(checkCoord(prey.x, n, xdirection), checkCoord(prey.y, n, ydirection)); //Ændrer koordinater for prey
            }

            ////PREDATOR
            int[] diff = {(prey.x - predator.x), (prey.y - predator.y)};
            //Vi laver et array med differensen af x og y koordinaterne for prey og predator.
            for(int j = 0; j <= 1; j++){ //Den tjekker for plads 0 og 1 i arrayet
                if (diff[j] < -s){ //Hvis værdien på den j'te plads
                    diff[j] = -s;
                } else if (diff[j] > s){
                    diff[j] = s;
                } else {
                    diff[j] = diff[j]; //Hvis diff[j] er i intervallet ]-s;s[
                }
            }
            predator.x += diff[0]; //diff[0] er et udtryk for preys bevægelse på x-aksen
            predator.y += diff[1];
        }
    }

    public static int getRandomCoord(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
        //Vi adderer max med 1 og subtraherer min med 1 for at inkludere min og max
        //Vi omregner til en int, da Math.random returnerer en double
    }

    public static int checkCoord(int coord, int max, int direction){ //Tjekker om et koordinat er inden for vores grid
        // coord er x/y koordinaten, max er n og direction er ændringen i x/y koordinaten
        if (coord < 0 && direction < 0){ //Hvis preys x/y koordinat samt retning er negativ
            return 0;
        } else if (coord > max - 1|| (coord < 0 && direction > 0)){
            //"&& direction > 0" er overflødigt, men inkluderet på grund af forståelse, da det bruges til at tage højde for overflow
            return max - 1;
        } else {
            return coord;
        }
    }
}
