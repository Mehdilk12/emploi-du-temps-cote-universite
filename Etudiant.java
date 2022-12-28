package tp5;

public class Etudiant 
{
    private int matricule;
    private String nomComplet;

    /**
     * Getter pour le nom complet de l'étudiant
     * @return : String
     */
    public String getNomComplet()
    {
        return nomComplet;
    }

    /**
     * Setter pour le nom complet de l'étudiant
     * @param nom : String
     */
    public void setNom(String nom)
    {
        this.nomComplet = nom;
    }

    /**
     * Getter pour la matricule de l'étudiant
     * @return : Integer
     */
    public int getMatricule()
    {
        return matricule;
    }

    /**
     * Setter pour la matricule de l'étudiant
     * @param matricule : Integer
     */
    public void setMatricule(int matricule)
    {
        this.matricule = matricule;
    }

}