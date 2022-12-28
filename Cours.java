package tp5;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cours 
{
    private HeureDesCours planDuCours;        // Objet de ce classe pour créer l'horaire d'un cours
    private HeureDesCours coursTheorique;     // Objet de ce classe pour avoir une historique des cours théoriques
    private HeureDesCours coursPratiques;     // Objet de ce classe pour avoir une historique des cours pratiques
    private String numeroCours;               // Le sigle avec le numero du cours
    private String nomDuProf; 
    private int credit;                       // Nombre de credit associé a ce cours
    private LocalDate intra;                  // Date de l'intra
    private ArrayList <LocalTime> intraHeure; // Heure de l'intra
    private LocalDate finale;                 // Date du final
    private ArrayList <LocalTime> finaleHeure;// Heure du final
    private LocalDate debutDateDuCours;       // Debut date du cours
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Personnaliser la date
    Locale fr = new Locale("fr", "FR"); // Pour imprimer le jour de la semaine en francais

    /**
     * Constructeur pour initialiser
     */
    public Cours()
    {
        planDuCours = new HeureDesCours();
        coursTheorique = new HeureDesCours();
        coursPratiques = new HeureDesCours();
        intraHeure = new ArrayList<LocalTime>();
        finaleHeure = new ArrayList<LocalTime>();
    }

    /**
     * Pour enlever les heures non désirées pour pouvoir rentrer les nouvelles heures
     * @param jour : Spécifier le jour pour la modification
     * @param typeCours : Quel type de cours, ex: cours théorique
     * @param plan : Horaire du cours
     */
    public void modification( String jour, HeureDesCours typeCours, HeureDesCours plan )
    {
        List < ArrayList<LocalTime> > type = typeCours.jourDeLaSemaine(jour);
        List < ArrayList<LocalTime> > horaire = plan.jourDeLaSemaine(jour);
        ArrayList <Integer> indexType = new ArrayList<Integer>();
        ArrayList <Integer> indexHoraire = new ArrayList<Integer>();

        for ( int i = 0; i < type.size(); i++)
        {
            ArrayList<LocalTime> heureType = type.get(i);

            for ( int j = 0; j < horaire.size(); j++)
            {
                ArrayList<LocalTime> heureHoraire = horaire.get(j);

                LocalTime debutHeureType = heureType.get(0);
                LocalTime finHeureType = heureType.get(1);
                LocalTime debutHorairePlan = heureHoraire.get(0);
                LocalTime finHorairePlan = heureHoraire.get(1);

                if ( debutHeureType.equals(debutHorairePlan) && finHeureType.equals(finHorairePlan) )
                {
                    indexType.add(i);
                    indexHoraire.add(j);
                }
            }
        }

        type.remove(indexType);
        horaire.remove(indexHoraire);
        /*for ( Integer i : indexType)
        {
            type.remove((int) i);
        }
        for ( Integer j : indexHoraire)
        {
            horaire.remove( (int) j);
        }*/
    }

    /**
     * Un setter pour ajouter les heures des cours a son horaire.
     * @param jour : Spécifier le jour pour la modification
     * @param type : Quel type de cours, ex: cours théorique
     * @param debutFin : Début et fin d'heure du cours
     */
    public void setPlanDuCours(String jour, String type,  ArrayList < LocalTime > debutFin)
    {
        planDuCours.addHoraireDuJour(jour, debutFin); 

        if ( (type.equals("cours théoriques")) && planDuCours.conflitHeure(planDuCours.jourDeLaSemaine(jour), debutFin))
        {
            coursTheorique.addHoraireDuJour(jour, debutFin);
        } else if ( (type.equals("cours pratiques")) && planDuCours.conflitHeure(planDuCours.jourDeLaSemaine(jour), debutFin))
        {
            coursPratiques.addHoraireDuJour(jour, debutFin);
        }
    }

    /**
     * Getter pour retourner l'horaire du cours
     * @return Classe HeureDesCours
     */
    public HeureDesCours getPlanDuCours()
    {
        return planDuCours;
    }

    /**
     * Getter pour retourner l'horaire du cours théorique
     * @return Classe HeureDesCours
     */
    public HeureDesCours getCoursTheorique()
    {
        return coursTheorique;
    }

    /**
     * Getter pour retourner l'horaire du cours pratique
     * @return Classe HeureDesCours
     */
    public HeureDesCours getCoursPratique()
    {
        return coursPratiques;
    }

    /**
     * Getter pour retourner le numero du cours
     * @return String
     */
    public String getNumeroCours()
    {
        return this.numeroCours;
    }

    /**
     * Setter pour le numero du cours
     * @param matiere : String
     */
    public void setNumeroCours( String matiere )
    {
        this.numeroCours = matiere;
    }

    /**
     * Getter pour le nom du professeur
     * @return : String
     */
    public String getNomDuProf()
    {
        return this.nomDuProf;
    }

    /**
     * Setter pour le nom du professeur
     * @param nomDuProf : String
     */
    public void setNomProf( String nomDuProf)
    {
        this.nomDuProf = nomDuProf;
    }

    /**
     * Getter pour le nombre de credit du cours
     * @return : Integer
     */
    public int getCredit()
    {
        return this.credit;
    }

    /**
     * Setter pour le nombre de credit du cours
     * @param credit : Integer
     */
    public void setCredit( int credit)
    {
        this.credit = credit;
    }

    /**
     * Getter pour la date de l'intra
     * @return : LocalDate
     */
    public LocalDate getIntra()
    {
        return this.intra;
    } 

    /**
     * Setter pour la date de l'intra
     * @param intra : LocalDate
     */
    public void setIntra(LocalDate intra)
    {
        //String date = format.format(intra);
        this.intra = intra;
    }

    /**
     * Getter pour l'heure de l'intra
     * @return : ArrayList <LocalTime>
     */
    public ArrayList <LocalTime> getIntraHeure()
    {
        return intraHeure;
    }

    /**
     * Setter pour l'heure de l'intra
     * @param intraHeure : ArrayList <LocalTime>
     */
    public void setIntraHeure( ArrayList <LocalTime> intraHeure)
    {
        this.intraHeure = intraHeure;
    }

    /**
     * Getter pour la date du final
     * @return : LocalDate
     */
    public LocalDate getFinale()
    {
        return finale;
    }

    /**
     * Setter pour la date du final
     * @param finale : LocalDate
     */
    public void setFinale(LocalDate finale)
    {
        //String date = format.format(finale);
        this.finale = finale;
    }

    /**
     * Getter pour l'heure du final
     * @return : ArrayList <LocalTime>
     */
    public ArrayList <LocalTime> getFinaleHeure()
    {
        return finaleHeure;
    }

    /**
     * Setter pour l'heure du final
     * @param finaleHeure : ArrayList <LocalTime>
     */
    public void setFinaleHeure( ArrayList <LocalTime> finaleHeure )
    {
        this.finaleHeure = finaleHeure;
    }

    /**
     * Getter pour le début date du cours
     * @return : LocalDate
     */
    public LocalDate getDebutDateDuCours()
    {
        return debutDateDuCours;
    }

    /**
     * Setter pour le début date du cours
     * @param debutDateDuCours : LocalDate
     */
    public void setDebutDateDuCours( LocalDate debutDateDuCours )
    {
        this.debutDateDuCours = debutDateDuCours;
    }

}
