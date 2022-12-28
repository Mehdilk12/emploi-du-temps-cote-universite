package tp5;

import java.io.IOException;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class NouveauHoraire 
{
    private Etudiant eleve;                     // Information sur l'étudiant
    private List < Cours > listeChoixCours;     // Liste des choix cours (MAX 10)
    private List < Cours > coursInscrit;        // Liste des cours inscrits
    static List < String > typeDeCours;         // Liste pour identifier les types de cours
    static List < String > jourDeLaSemaine;     // Liste pour identifier le jour de la semaine
    private int nbreDeCours;                    // Pour tracer le nombre de cours entré 
    private int nbreCredit;                     // Pour tracer le nombre de crédit
    private String debutSession;                // Date du début session
    private String finSession;                  // Date du fin session
    DateTimeFormatter format;                   // Formatter pour les dates
    DateTimeFormatter heureFormat;              // Formatter pour les heures en 24h
    Locale fr = new Locale("fr", "FR"); // Formatter pour imprimer les jours en francais
    Scanner input;

    /**
     * Constructeur pour initialiser
     */
    public NouveauHoraire() throws IOException, ParseException
    {
        eleve = new Etudiant();
        listeChoixCours = new ArrayList<Cours>(10);
        coursInscrit = new ArrayList<Cours>();
        typeDeCours = new ArrayList<String>(Arrays.asList("cours théoriques", "cours pratiques", "intra", "final"));
        jourDeLaSemaine = new ArrayList<String>(Arrays.asList("lundi", "mardi", "mercredi", "jeudi", "vendredi"));
        //nbreDeCours = 0;
        nbreDeCours = 0;
        nbreCredit = 0;
        format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        heureFormat = DateTimeFormatter.ofPattern("HH:mm");
        input = new Scanner(System.in);
        dossierEleve();
    }

    /**
     * Pour remplir les informations de l'étudiant
     * @throws IOException
     * @throws ParseException
     */
    public void dossierEleve() throws IOException, ParseException
    {
        try
        {
            System.out.println("------- NOUVEAU HORAIRE -------\n");
            System.out.println("ATTENTION!!! Maximum de crédit: 14\n");
            System.out.println("Rentrer votre matricule: ");
            while (input.hasNextInt())
            {
                int numeroMatricule = input.nextInt();
                String length = Integer.toString(numeroMatricule); // Pour vérifier la longueur de matricule rentre par TGDE
                if ( length.length() != 8) 
                {
                    System.out.println("Entrez un nombre de longueur de 8 SVP. ");
                } else {
                    eleve.setMatricule(numeroMatricule);
                    break;
                }
            }

            System.out.println("\nRentrer votre prénom: ");

            if ( !input.hasNextInt() )
            {
                String nomComplet = "";
                nomComplet += input.next();
                System.out.println("\nRentrer votre nom: ");
                if ( !input.hasNextInt() )
                {
                    nomComplet += input.next();
                    input.nextLine();
                    eleve.setNom(nomComplet);
                }
            }

        } catch (IllegalArgumentException e)
        {
            System.out.println( " mauvais input : " + e);
        }
        session();
        informationDuCours();
    }

    /**
     * Pour rentrer les dates de la session
     * @throws ParseException
     */
    public void session() throws ParseException
    {
        String debutDate, finDate;
        System.out.println("\nEntrez la date du début de la session ( jj/mm/yyyy ): ");
        debutDate = input.nextLine(); //prend input dans var
        LocalDate debutInString = LocalDate.parse(debutDate, format);
        debutDate = format.format(debutInString); //from localdate
        setDebutSession(debutDate); // prend en param LocalDate pour le changer en String

        System.out.println("\nEntrez la date du fin de la session ( jj/mm/yyyy ): ");
        finDate = input.nextLine();
        LocalDate finInString = LocalDate.parse(finDate,format);
        finDate = format.format(finInString); // prend en param LocalDate pour le changer en String
        setFinSession(finDate); 
    }

    /**
     * Pour créer le liste des choix des cours avec les détails 
     * @throws ParseException
     */
    public void informationDuCours() throws ParseException
    {
        input = new Scanner(System.in);
        int nombre;
        Cours choixCours = null;
        String numeroCours, sigle;
        
        while ( checkCours() )
        {
            choixCours = new Cours();
            System.out.println("ASKING FOR THE CLASSES");
            nbreDeCours++; // keep track du nbre du cours
            System.out.println("Entrez le sigle du cours " + nbreDeCours + " ( ex: IFT ): ");
            
            while ( true )
            {
                sigle = input.next().toUpperCase(); // mettre sigle dans var
                input.nextLine();
                if ( sigle.length() != 3 )
                {
                    System.out.println("\nLe sigle prend seulement 3 lettres. Réessayez de nouveau.");
                } else
                break;
            }

            numeroCours = sigle; //concat pour faire IFT1025
            System.out.println("\nEntrez le numéro du cours " + nbreDeCours + " ( ex: 1025 ): ");
            if(input.hasNextInt())
            {
                while ( true )
                {
                    nombre = input.nextInt();
                    input.nextLine();
                    String tempNumero = Integer.toString(nombre);
                    if ( tempNumero.length() != 4 )
                    {
                        System.out.println("\nLe numéro prend seulement 4 chiffres. Réessayez de nouveau.");
                    } else
                    break;
                }
                
                numeroCours += nombre;
                choixCours.setNumeroCours(numeroCours); //dans la classe 

                System.out.println("\nEntrez le nombre de crédit ( entre 0 et 4 ): ");
                int creditCours = input.nextInt();
                if ( creditCours < 5)
                {
                    choixCours.setCredit(creditCours);
                }
            }

            System.out.println("\nEntrez le prénom et nom du professeur: ");
            input.nextLine();
            String nomDuProf = input.nextLine();
            choixCours.setNomProf(nomDuProf);
            listeChoixCours.add(choixCours);
        }
        action();
    }

    /**
     * Action pour personnaliser l'horaire
     */
    public void action()
    {
        boolean actions = true;
        String rep;
        while (actions)
        {
        	
            System.out.println("\n---- Menu ----\n\n1. Ajouter un cours à l'horaire\n2. Supprimer un cours de l'horaire\n3. Terminer (pas faite encore)\n");
            int reponse = input.nextInt();
            input.nextLine();
            if ( reponse == 1 )
            {
                while (true)
                {
                    ajouterCours();
                    System.out.println("Voulez-vous rajouter d'autre cours? ( oui ou non )");
                    rep = input.next();
                    input.nextLine();
                    if (rep.equalsIgnoreCase("non")) {
                        break;
                    }
                }
            } else if ( reponse == 2)
            {
                supprimerCours();
                
            } else if ( reponse == 3)
            {
                System.out.println("ON SORT***");
                break;
            }
        }
    }

    /**
     * Pour supprimer un cours désiré
     */
    public void supprimerCours()
    {
        ///if the list is empty no point in doing this
        while (true)
        {
            System.out.println("\nQuel cours voulez-vous supprimer? ( ex: ift1025 )");
            String nomCours = input.next();
            input.nextLine();
            for ( Cours c : listeChoixCours)
            {
                if (c.getNumeroCours().equals(nomCours) )
                {
                    int i = listeChoixCours.indexOf(c);
                    listeChoixCours.remove(i);
                    break;
                }
            }
            System.out.println("Voulez-vous supprimer d'autre cours? ( oui ou non )");
            String rep = input.next();
            input.nextLine();
            if ( rep.equalsIgnoreCase("non"))
            {
                break;
            }
        }
    }

    /**
     * Pour ajouter un cours
     */
    public void ajouterCours()
    {
        Cours ceCours = null;

        while ( checkCredit() ) // Vérifier si ca n'excede pas 14 credits pour la session
        {
            System.out.println("\nQuel cours voulez-vous ajouter? ( ex: ift1025 )");
            String nomCours = input.nextLine();
            Cours coursInput = checkDansListeCours(nomCours); // Vérifier s'il se présente dans la liste des choix de cours

            if ( coursInput != null ) //si oui
            {
                if ( !checkDansListeInscrit(nomCours) ) // check si ce n'est pas déjà dans les cours inscrits
                {
                    ceCours = coursInput;
                    nbreCredit += ceCours.getCredit();
                    System.out.println(" nombre de CREDIT a date: " + nbreCredit + "\n");
                    break;

                } else { 
                    System.out.println("Déjà inscrit dans ce cours. Veuillez choisir un autre cours.\n");
                }
            } else { 
                System.out.println("\nPas dans la liste des choix de cours !!!");
            }
        }
        
        boolean encoreDesJours, semaine;
        String jours = "";
        for ( String type : typeDeCours ) // Boucle pour passer par tout le type de cours ( theorique, pratique ou examen )
        {
            String tempDate;
            semaine = true;

            while ( semaine )
            {
                boolean loin = false;
                LocalDate localDate = null;
                while (!loin)
                {
                    if ( (type.equals("intra")) || type.equals("final"))
                    {
                        System.out.println("Entrez la date de l'examen " + type + " ( jj/mm/yyyy ): "); // enter 09/11/2022

                    } else
                    {
                        System.out.println("Entrez la date du début des " + type + " ( jj/mm/yyyy ): "); // enter 17/10 pour intra
                    }

                    tempDate = input.nextLine(); 
                    localDate = LocalDate.parse(tempDate,format); 
                    tempDate = format.format(localDate); //String
                    System.out.println("     Date has been parsed!!!!!!!");


                    if ( type.equals("intra")) 
                    {
                        loin = checkIntra(tempDate, ceCours, localDate);

                    } else if ( type.equals("final") )
                    {
                        loin = checkFinal(tempDate, ceCours, localDate);

                    } else {
                        ceCours.setDebutDateDuCours(localDate);
                        loin = true;
                    }
                }
                
                DayOfWeek dayOfWeeks = localDate.getDayOfWeek(); //type du jour de la semaine

                ///////////test to see what day it is
                jours = dayOfWeeks.getDisplayName(TextStyle.FULL, fr); //////tester a enlever apres
                System.out.println("jour de la semaine: "+jours);
                //////////////

                // Pas possible d'avoir des cours durant les fins de semaine
                if (jours.equals("samedi") || (jours.equals("dimanche")))
                {
                    System.out.println("Ceci est un fin de semaine. SVP rentrez une date de la semaine. ");
                } else {
                    semaine = false;
                }
            }
            
            encoreDesJours = true;
            while (encoreDesJours)
            {
                String tempHeure;
                String tempHeure2;

                ArrayList < LocalTime > debutFinCours = new ArrayList<>();

                System.out.println("Entrez l'heure début de " + type + " ( ex: 09:00, non 9:00 ): ");
                tempHeure = input.nextLine();
                LocalTime tempDebutHeure = parseTime(tempHeure);
                debutFinCours.add(tempDebutHeure);
                System.out.println(tempDebutHeure);
                
                System.out.println("Entrez l'heure fin de " + type + " ( ex: 13:00 ): ");
                tempHeure2 = input.nextLine();
                LocalTime tempFinHeure = parseTime(tempHeure2);
                debutFinCours.add(tempFinHeure);
                System.out.println(tempFinHeure);

                if ( !(type.equals("intra")) && !(type.equals("final")) && checkHeureDuJour(ceCours ,jours, debutFinCours) )
                {
                    System.out.println("answer from checkHeuredujour: " + checkHeureDuJour(ceCours, jours, debutFinCours));///// a enlever
                    ceCours.setPlanDuCours(jours, type, debutFinCours);

                    System.out.println("Est-ce que ce cours apparaîsse sur d'autre jours de la semaine? ( oui ou non )");
                    String reponse = input.next();

                    if ((reponse).equalsIgnoreCase("oui"))
                    {
                        System.out.println("Quel est le jour?");
                        jours = input.next().toLowerCase();
                        input.nextLine();
                    } else if ( (reponse).equalsIgnoreCase("non") ) // when they say no
                    { 
                        input.nextLine();
                        encoreDesJours = false;
                    } else {
                        input.nextLine();
                        semaine = false;
                        break;
                    }
                } else if ( (type.equals("intra"))  )
                {
                    ceCours.setIntraHeure(debutFinCours);
                    encoreDesJours = false;

                } else if ( (type.equals("final")) )
                {
                    ceCours.setFinaleHeure(debutFinCours);
                    encoreDesJours = false;

                } else {
                    System.out.println("ERREUR: Conflit d'horaire avec un autre cours!!!\n");
                }
            }
        }
        coursInscrit.add(ceCours); // add ce cours in LIST des cours on veut s'inscrire
    }

    /**
     * Pour faire une modification à l'horaire du cours désiré
     * @param numeroCours
     */
    public void modifierCours( String numeroCours )
    {
        for ( Cours coursLoop : coursInscrit)
        {
            if ( coursLoop.getNumeroCours().equals(numeroCours))
            {
                System.out.println("Options à modifier: \n1. Cours théoriques\n2. Cours pratiques\n3. Intra\n4. Final\n");
                int choix = input.nextInt();
                input.nextLine();
                
                // Choisi de modifier les cours
                if ( (choix == 1) || (choix == 2) )
                {
                    (coursLoop.getPlanDuCours()).displayCours(); // montrer horaire hebdomadaire

                    System.out.println("Quel jour à modifier?\n");
                    String jour = input.nextLine();

                    ArrayList <LocalTime> timeList = new ArrayList<LocalTime>();  // Sauvegarder les heures désirées dans une liste
                    System.out.println("Entrez l'heure début du cours: ");
                    timeList.add(parseTime(input.nextLine()));
                    System.out.println("Entrez l'heure fin du cours: ");
                    timeList.add(parseTime(input.nextLine()));

                    if ( choix == 1 )
                    {
                        coursLoop.modification(jour, coursLoop.getCoursTheorique(), coursLoop.getPlanDuCours());
                    } else {
                        coursLoop.modification(jour, coursLoop.getCoursPratique(), coursLoop.getPlanDuCours());
                    }
                    break;
                   
                // Choisi de modifier l'intra
                } else if ( choix == 3 )
                {
                    System.out.println("Entrez la date d'examen intra: ");
                    String dateInString = input.nextLine();
                    LocalDate localDate = LocalDate.parse(dateInString,format);
                    dateInString = format.format(localDate); //String in diff format
                    boolean faite = checkIntra(dateInString, coursLoop, localDate);
                    ArrayList < LocalTime > timeList = new ArrayList<>();
                    System.out.println("Entrez l'heure début d'examen intra ( ex: 09:00 ): ");
                    LocalTime time = parseTime(input.nextLine());
                    timeList.add(time);
                    System.out.println("Entrez l'heure fin d'examen intra ( ex: 09:00 ): ");
                    time = parseTime(input.nextLine());
                    timeList.add(time);
                    coursLoop.setIntraHeure(timeList);
                    break;

                // Choisi de modifier le final
                } else if ( choix == 4 )
                {
                    System.out.println("Entrez la date d'examen final: ");
                    String dateInString = input.nextLine();
                    LocalDate localDate = LocalDate.parse(dateInString,format);
                    dateInString = format.format(localDate); //String in diff format
                    boolean faite = checkIntra(dateInString, coursLoop, localDate);
                    ArrayList < LocalTime > timeList = new ArrayList<>();
                    System.out.println("Entrez l'heure début d'examen final ( ex: 09:00 ): ");
                    LocalTime time = parseTime(input.nextLine());
                    timeList.add(time);
                    System.out.println("Entrez l'heure fin d'examen final ( ex: 09:00 ): ");
                    time = parseTime(input.nextLine());
                    timeList.add(time);
                    coursLoop.setFinaleHeure(timeList);
                    break;
                }
            }
        }
    }

    /**
     * Vérifier pour des conflit dans l'horaire de l'étudiant
     * @param cours : nom du cours choisi
     * @param jour : le jour de la semaine 
     * @param heure : les heures désirées à implémenter dans l'horaire
     * @return : boolean pour poursuivre à la prochaine étape
     */
    public boolean checkHeureDuJour(Cours cours, String jour, ArrayList <LocalTime> heure)
    {
        System.out.println("In checkHeureDuJour ...");
        HeureDesCours h;
        List < ArrayList<LocalTime> > horaireDuJour;

        System.out.println("In coursInscrit : " + coursInscrit);
        if (coursInscrit.isEmpty())
        {
            h = cours.getPlanDuCours();
            horaireDuJour = h.jourDeLaSemaine(jour);
            System.out.println("answer from conflitHeure:  " + h.conflitHeure(horaireDuJour, heure));
            if ( h.conflitHeure(horaireDuJour, heure))
            {
                return false;
            }
        } else 
        {
            for (Cours c : coursInscrit)
            {
                h = c.getPlanDuCours();
                horaireDuJour = h.jourDeLaSemaine(jour);
                System.out.println("answer from conflitHeure:  " + h.conflitHeure(horaireDuJour, heure));
                if ( h.conflitHeure(horaireDuJour, heure))
                {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Pour vérifier si le cours désiré à s'inscrire n'est pas parti du liste des cours déjà inscrits
     * @param cours : nom du cours
     * @return
     */
    public boolean checkDansListeInscrit( String cours )
    {
        for ( Cours c : coursInscrit )
        {
            if ( c.getNumeroCours().equalsIgnoreCase(cours) )
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Pour vérifier si le cours fait parti de la liste des choix de cours
     * @param cours : nom du cours
     * @return : Cours s'il est présente dans la liste
     */
    public Cours checkDansListeCours( String cours )
    {
        for ( Cours c : listeChoixCours)
        {
            if ( c.getNumeroCours().equalsIgnoreCase(cours) )
            {
                return c;
            }
        }
        return null;
    }

    /**
     * Pour vérifier si le nombre de crédit ne dépasse pas la limite de 14
     * @return : boolean
     */
    public boolean checkCredit()
    {
        if (nbreCredit > 15)
        {
            return false;
        } 
        return true;
    }

    /**
     * Pour vérifier si la liste des choix de cours ne dépasse pas la limite de 10
     * @return : boolean
     */
    public boolean checkCours()
    {
        if (nbreDeCours > 9)
        {
            return false;
        }
        return true;
    }

    /**
     * Pour vérifier si l'intra d'un cours ne conflit pas avec les intras des autres cours
     * @param date : Date de l'intra
     * @param cours : Nom du cours pour mettre la date désirée de l'intra 
     * @param localDate : Date de l'intra en LocalDate
     * @return
     */
    public boolean checkIntra( String date, Cours cours, LocalDate localDate )
    {
        if ( !coursInscrit.isEmpty() )
        {
            System.out.println("    list is NOT EMPTY");///A ENLEVER APRES
            int memeDate = 0; // Pour tracer si un cours possède la même date
            for ( Cours tempCours : coursInscrit)
            {
                if ( (tempCours.getIntra().toString()).equals(date) ) 
                {
                    System.out.println("Rentrez une autre date qui ne conflit pas avec celle d'un autre cours");
                    memeDate++;
                    break;
                }

            }
            if (memeDate == 0)
            {
                cours.setIntra(localDate);

            }
            return true;
        } else {
            System.out.println("    list is empty");///A ENLEVER APRES
            cours.setIntra(localDate);
            return false;
        }
    }

    /**
     * Pour vérifier si le final d'un cours ne conflit pas avec les finaux des autres cours
     * @param date : Date du final
     * @param cours : Nom du cours pour mettre la date désirée du final 
     * @param localDate : Date du final en LocalDate
     * @return
     */
    public boolean checkFinal( String date, Cours cours, LocalDate localDate)
    {
        if (!coursInscrit.isEmpty())
        {
            int memeDate = 0;// Pour tracer si un cours possède la même date
            for ( Cours tempCours : coursInscrit)
            {
                if ( (tempCours.getFinale().toString()).equals(date) ) // equals can only compare w strings
                {
                    System.out.println("ERREUR: Rentrez une autre date qui ne conflit pas avec celle d'un autre cours");
                    memeDate++;
                    break;
                } else if ((tempCours.getIntra().until(tempCours.getFinale(), ChronoUnit.DAYS)) > 30 )
                {
                    System.out.println("ERREUR: Rentrez un date plus que 30 jours de la date de l'intra");
                    break;
                }
            }
            if (memeDate == 0)
            {
                cours.setIntra(localDate);
            }
            return true;
        } else {
            System.out.println("    list is empty");///A ENLEVER APRES
            cours.setFinale(localDate);
            return false;
        }

    }

    /**
     * Getter pour le dossier eleve
     * @return : Classe Etudiant
     */
    public Etudiant getEleve()
    {
        return eleve;
    }

    /**
     * Getter pour la liste des cours inscrits
     * @return
     */
    public List <Cours> getCoursInscrit()
    {
        return coursInscrit;
    }

    /**
     * Pour imprimer la liste des cours inscrits
     */
    public void displayCoursInscrit()
    {
        for (Cours coursLoop : coursInscrit)
        {
            System.out.println( coursLoop.getNumeroCours() );
        }
    }

    /**
     * Parse l'heure désirée
     * @param time
     * @return
     */
    public LocalTime parseTime( String time )
    {
        System.out.println("in the parse time method !!!"); // A ENELEVERR !!!!!!!!!!!!!!!!!!
        LocalTime localTime = LocalTime.parse( time, heureFormat);
        return localTime;
    }

    /**
     * Getter pour la date du début de la session
     * @return : String
     */
    public String getDebutSession()
    {
        return debutSession;
    }
    
    /**
     * Setter pour la date du début de la session
     * @param debutSession : String
     */
    public void setDebutSession( String debutSession )
    {
        this.debutSession = debutSession;
    }

    /**
     * Getter pour la date de la fin session
     * @return
     */
    public String getFinSession()
    {
        return finSession;
    }

    /**
     * Setter pour la date de la fin session
     * @param finSession
     */
    public void setFinSession (String finSession)
    {
        this.finSession = finSession;
    }

    /**
     * Pour imprimer l'horaire de l'etudiant
     */
    public void displayHoraire()
    {
        Cours horaire = new Cours();
        for ( Cours sonCours : coursInscrit)
        {
            HeureDesCours sonPlan = sonCours.getPlanDuCours();
            for ( String jour : jourDeLaSemaine)
            {
                if ( !(sonPlan.jourDeLaSemaine(jour).isEmpty()) )
                {
                    List < ArrayList<LocalTime> > heureListDuJour = sonPlan.jourDeLaSemaine(jour);
                    for ( ArrayList<LocalTime> heure : heureListDuJour )
                    {
                        horaire.getPlanDuCours().jourDeLaSemaine(jour).add(heure);
                    }

                }
            }
        }
    }

    /*public static void main(String[] args) throws IOException {
        NouveauHoraire nouveau = new NouveauHoraire();
        nouveau.dossierEleve();
    }*/
    
}
