package tp5;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HeureDesCours 
{
    List < ArrayList<LocalTime> > lundi;      // List pour représenter la plage d'horaire de chaque jour de la semaine
    private List < ArrayList<LocalTime> > mardi;
    private List < ArrayList<LocalTime> > mercredi;
    private List < ArrayList<LocalTime> > jeudi;
    private List < ArrayList<LocalTime> > vendredi;
    DateTimeFormatter format;

    /**
     * Constructeur pour initialiser
     */
    public HeureDesCours()
    {
        format = DateTimeFormatter.ofPattern("HH:mm"); // Un formatter pour représenter les heures en 24h
        lundi = new ArrayList < ArrayList<LocalTime> >();
        mardi = new ArrayList < ArrayList<LocalTime> >();
        mercredi = new ArrayList < ArrayList<LocalTime> >();
        jeudi = new ArrayList < ArrayList<LocalTime> >();
        vendredi = new ArrayList < ArrayList<LocalTime> >();
    }

    /**
     * Retourner la liste du jour de la semaine spécifiée
     * @param j : Le jour spécifié de la semaine
     * @return : List < ArrayList<LocalTime> >
     */
    public List < ArrayList<LocalTime> > jourDeLaSemaine(String j)
    {
        List < ArrayList<LocalTime> > jour = null;

        if ( j.equals("lundi")) {
            jour = lundi;
        } else if ( j.equals("mardi")) {
            jour = mardi;
        } else if ( j.equals("mercredi")) {
            jour = mercredi;
        } else if ( j.equals("jeudi")) {
            jour = jeudi;
        } else if ( j.equals("vendredi")) {
            jour = vendredi;
        }
        return jour;
    }

    /**
     * Pour vérifier s'il y aura lieu d'un conflit d'horaire
     * @param jour : Vérifier l'horaire de ce jour de la semaine 
     * @param nouveauHeure : Contenant les nouvelles heures désirées à implémenter dans l'horaire 
     * @return : boolean pour indiquer l'apparence du conflit d'horaire
     */
    public boolean conflitHeure ( List < ArrayList<LocalTime> > jour, List < LocalTime > nouveauHeure) //nouveauHeure only has debut et fin
    {
        ArrayList <Boolean> conflit = new ArrayList<Boolean>(); // Pour laisser des traces du lieu du conflit

         ///pour tester
        System.out.println("IN CONFLIT HEURE METHOD...");
        System.out.println(" BEFORE MODIFICATION: ");
        for ( ArrayList<LocalTime> jourr : jour)
        {
            for ( LocalTime jourrr : jourr)
            {
                System.out.println(jourrr);
            }
        }
       
        for ( LocalTime newheure : nouveauHeure)
        {
            System.out.println("    HEURE INPUT :   " + newheure);
        }
        /////////

        if ( jour.isEmpty() )
        {
            System.out.println("List is null");//////tester
            return false;

        } else {
            
            for ( List < LocalTime > heure : jour) // Rentrer dans liste des intervalles d'heures
            {
                LocalTime debut = heure.get(0);             // Heure début d'un cours dans l'horaire du jour
                LocalTime fin = heure.get(1);               // Heure fin d'un cours dans l'horaire du jour
                LocalTime tempDebut = nouveauHeure.get(0);  // Heure début d'un cours désirée à rajouter
                LocalTime tempFin = nouveauHeure.get(1);    // Heure fin d'un cours désirée à rajouter

                System.out.println("debut in list: " + debut);//////tester
                System.out.println("fin in list :  " + fin);/////tester

                if ( !tempDebut.equals(debut) )
                {
                    System.out.println("new debut hour not equal to debut in list");/////tester
                    if ( tempDebut.isBefore(debut) )
                    {
                        System.out.println("new debut is before debut in list");/////tester
                        if (tempFin.isBefore(debut) || (tempFin.equals(debut)))
                        {
                            System.out.println(" no conflit ???");/////tester
                            conflit.add(false);
                        } else {
                            System.out.println("ERREUR: CONFLIT D'HORAIRE !!!!!");/////tester
                            conflit.add(true);
                        }
                    } else
                    {
                        if ( tempDebut.isBefore(fin) )
                        {
                            System.out.println("ERREUR: CONFLIT D'HORAIRE !!!!!");
                            conflit.add(true);
                        } else {
                            conflit.add(false);
                        }
                    }
                } else 
                {
                    System.out.println("ERREUR: CONFLIT D'HORAIRE !!!!!");
                    conflit.add(true);;
                }
            }            
            if( conflit.contains(true))
            {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Pour ajouter les heures désirées à rajouter dans l'horaire du jour
     * @param jour : Jour de la semaine
     * @param debutfinCours : les heures du début et fin du cours
     */
    public void addHoraireDuJour (String jour, ArrayList <LocalTime> debutfinCours)
    {
        List < ArrayList <LocalTime> > jourDeLaSemaine = jourDeLaSemaine(jour);

        if ( !conflitHeure(jourDeLaSemaine, debutfinCours) )
        {   
            jourDeLaSemaine.add(debutfinCours);     
            ArrayList <LocalTime> pos1 = new ArrayList<LocalTime>();
            ArrayList<LocalTime> min = new ArrayList<LocalTime>();
            ArrayList<LocalTime> tempPos = new ArrayList<LocalTime>();
            int tempIndex;

            // Algorithm de triage
            for ( int i = 0; i < jourDeLaSemaine.size(); i++ ) 
            {
                min = jourDeLaSemaine.get(i);
                LocalTime minHeure = min.get(0);
            
                for ( int j = i+1; j < jourDeLaSemaine.size(); j++ )
                {
                    pos1 = jourDeLaSemaine.get(j);
                    LocalTime heure1 = pos1.get(0);

                    if (heure1.isBefore(minHeure))
                    {
                        min = pos1;
                        minHeure = heure1;
                    }
                }
                tempPos = jourDeLaSemaine.get(i);
                tempIndex = jourDeLaSemaine.indexOf(min);
                jourDeLaSemaine.set(i, min);
                jourDeLaSemaine.set(tempIndex, tempPos);
            }       
        } else {
            System.out.println("Il y a un conflit d'horaire, SVP réessayez de nouveau.");
        }

        ////////tester///////
        System.out.println("PRINTING THE LIST...");
        for ( ArrayList <LocalTime> jourr : jourDeLaSemaine )
        {
            for ( LocalTime jourrr : jourr)
            {
                System.out.println("    "+ jourrr);
            }
        }
        ////////
    }

    public void displayCours()
    {
        ArrayList <String> semaine = new ArrayList<>(Arrays.asList("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi"));
        for ( String jour : semaine )
        {
            System.out.println(jour + ": ");

            if (!(jourDeLaSemaine(jour)).isEmpty())
            {
                for ( ArrayList <LocalTime> time : (jourDeLaSemaine(jour)) )
                {
                    System.out.println(time.get(0) + "-" + time.get(1));
                }
            }
        }
    }

    /*public void displayCours(int id)
    {
        if ( id == 1)
        {
            System.out.println(coursTheorique);
            //for ( ArrayList <String> jourALaFois: )
        } else if ( id == 2 )
        {
            System.out.println(coursPratique);
        }
    }*/
    /*public void addHeureCours( String jour, ArrayList < LocalTime > heureTime)
    {
        ArrayList <String> timeList = new ArrayList<String>();
        timeList.add(jour);
        timeList.add(heureTime.get(0).toString());
        timeList.add(heureTime.get(1).toString());
        
    }*/

    public static void main(String[] args) {

      


    }

}
