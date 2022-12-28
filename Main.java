package tp5;
//tarvaille fait par mohamed mehdi lakhdhar 20181433 et Trinh Ngo 20228204


import java.io.IOException;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Constructeur pour garder le programme rouler
 */
public class Main 
{
    static List< NouveauHoraire > etudiants = new ArrayList<>();

   

    public static void main(String[] args) throws Exception {
    {//test(); enlever du commentaire pour la faire demarrer 
    	Scanner input;
    boolean rouler;
    NouveauHoraire horaire;
        rouler = true;
        input = new Scanner(System.in);
        System.out.println("Choisissez le trimestre:\n1. Automne");

        if (input.nextInt() != 1)
        {
            throw new Exception("ce choix n existe pas  "); 
        }

        while (rouler) // boolean pour le garder rouler
        {
            System.out.println("---- Menu ----\n1. Créer horaire\n2. Accès à l'horaire\n3. Terminer");
            int choix = input.nextInt();

            if( choix == 1)
            {
                horaire = new NouveauHoraire();
                etudiants.add(horaire);
             

            } 
            else if((choix == 2) && (!etudiants.isEmpty())) 
            {
                input.nextLine(); 
                System.out.println("Rentrer votre matricule: ");

                if (input.hasNextInt())
                {
                    int matricule = input.nextInt(); //matricule d'etudiant choisi pour accéder son horaire
                    for ( NouveauHoraire etudiant : etudiants ) // boucle pour trouver l'étudiant correspondant
                    {
                        int m = etudiant.getEleve().getMatricule();
                        if ( m == matricule)
                        {
                            // Menu
                            System.out.println("\n1. Afficher l'horaire\n2. Modifier l'horaire *** Cette action effacera l'horaire du jour pour ce cours courant et le remplace avec le nouveau ***\n");
                            choix = input.nextInt();
                            input.nextLine();
                            
                            if ( choix == 1)
                            {
                                System.out.println( "Horaire de " + etudiant.getEleve().getNomComplet() + ":"); 
                                etudiant.displayHoraire();
                                System.out.println("Date de début session: " + etudiant.getDebutSession() + "\nDate de fin session: " + etudiant.getFinSession());
                                System.out.println("Date de l'intra: " );
                                for ( Cours cours : etudiant.getCoursInscrit())
                                {
                                    System.out.println(cours.getNumeroCours() + ": " + cours.getIntra()+ "de " + cours.getIntraHeure().get(0) + " à " + cours.getIntraHeure().get(1));
                                }
                                System.out.println("Date du final: " );
                                for ( Cours cours : etudiant.getCoursInscrit())
                                {
                                    System.out.println(cours.getNumeroCours() + ": " + cours.getFinale()+ "de " + cours.getFinaleHeure().get(0) + " à " + cours.getFinaleHeure().get(1));
                                }

                            } else if ( choix == 2 )
                            {
                                while (true)
                                {
                                    System.out.println("Quel cours à modifier?\n ");
                                    etudiant.displayCoursInscrit();
                                    String courString = input.nextLine();
                                    etudiant.modifierCours(courString);
                                    break;
                                }
                            }
                        }else {throw new Exception("ce choix n existe pas  ");  }
                    }
                }

            } else if (choix==2)
            {System.out.println("y a pas d horaire puisque y a pas d etudiant inscrit  ");
            }
            	
            
            
            
            else if (choix==3)
            {                System.out.println("fin du programme ");
 break;
            } else
            {
                System.out.println("Choisir entre 1 ou 2 SVP: ");
            }
        
        throw new IOException();}}
    }

    public static void test ()  {
    	
    	  ArrayList <LocalTime> testList = new ArrayList<LocalTime>();
          LocalTime test1 = LocalTime.parse("07:30");
          LocalTime test2 = LocalTime.parse("09:30");
          
          testList.add(test1);
          testList.add(test2);
          HeureDesCours test = new HeureDesCours();
          System.out.println("****Entering the method.... *****");
          test.addHoraireDuJour("lundi", testList);

          //second test: meme heure --> conflit in horaire
          testList = new ArrayList<LocalTime>();
          LocalTime test3 = LocalTime.parse("07:30");
          LocalTime test4 = LocalTime.parse("09:30");
          testList.add(test3);
          testList.add(test4);
          System.out.println("****Entering the method again.... *****");
          test.addHoraireDuJour("lundi", testList);

          //troisieme test: ajouter nouveau heure apres 7:30-9:30
          testList = new ArrayList<LocalTime>();
          LocalTime test5 = LocalTime.parse("09:30");
          LocalTime test6 = LocalTime.parse("10:30");
          testList.add(test5);
          testList.add(test6);
          System.out.println("****Entering the method for the third time .... *****");
          test.addHoraireDuJour("lundi", testList);

          //4ieme test: ajouter nouveau heure avant 7:30-9:30
          testList = new ArrayList<LocalTime>();
          LocalTime test7 = LocalTime.parse("05:00");
          LocalTime test8 = LocalTime.parse("07:00");
          testList.add(test7);
          testList.add(test8);
          System.out.println("****Entering the method for the fourth time .... *****");
          test.addHoraireDuJour("lundi", testList);}
        
        public static void assertTest(boolean test, String message) {
        	 if(test) {
        	     System.out.println("OK: " + message);
        	 } else {
        	     System.out.println("ERREUR: " + message);
        	 }
        
    }
    
}
