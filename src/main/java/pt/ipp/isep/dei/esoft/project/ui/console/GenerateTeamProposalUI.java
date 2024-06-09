package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.CollaboratorController;
import pt.ipp.isep.dei.esoft.project.application.controller.SkillController;
import pt.ipp.isep.dei.esoft.project.application.controller.TeamController;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.domain.Team;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Generate team proposal ui.
 */
public class GenerateTeamProposalUI implements Runnable {

    private final CollaboratorController collaboratorController;
    private final SkillController skillController;
    private final TeamController teamController;

    /**
     * Instantiates a new Generate team proposal ui.
     */
    public GenerateTeamProposalUI() {
        this.collaboratorController = new CollaboratorController();
        this.skillController = new SkillController();
        this.teamController = new TeamController();
    }

    @Override
    public void run() {

        System.out.println("\n-----Generate a team proposal automatically.-----\n");
        System.out.println("To Choose the team requirements in one line choose the following format:\nMaximum Team Size; Minimum Team Size; <Required Skill Name> <Required Skill Name> ... <Required Skill Name N>");
        System.out.println("To Choose the values separately for a more user friendly format just press enter.");

        String teamRequirements = Utils.readLineFromConsole("Enter the team requirements: ").trim();

        if (teamRequirements.isEmpty()) {
            List<Skill> requiredSkills = new ArrayList<>();
            while (Utils.confirm("Do you want to add a required skill? (S/N)")) {
                List<Skill> skillList = skillController.getSkillList();
                int skillIndex = Utils.showAndSelectIndex(skillList, "Select a skill: ");
                requiredSkills.add(skillList.get(skillIndex));
            }

            int maxTeamSize = Utils.readIntegerFromConsole("Enter the maximum team size: ");
            int minTeamSize = Utils.readIntegerFromConsole("Enter the minimum team size: ");
            generateTeamProposal(minTeamSize, maxTeamSize, requiredSkills);

        } else {
            while (teamRequirements.contains("<")) {
                teamRequirements = teamRequirements.replace("<", "");
            }

            while (teamRequirements.contains(">")) {
                teamRequirements = teamRequirements.replace(">", "");
            }


            String[] requirements = teamRequirements.split(";");

            if (requirements.length < 3) {
                System.out.println("Invalid format. Operation cancelled.");
                return;
            }

            try {
                int maxTeamSize = Integer.parseInt(requirements[0].trim());
                int minTeamSize = Integer.parseInt(requirements[1].trim());

                List<String> requiredSkills = new ArrayList<>(Arrays.asList(requirements).subList(2, requirements.length));

                List<Skill> skills = new ArrayList<>();
                for (String skillName : requiredSkills) {
                    if (skillName.equals("None") || skillName.isEmpty()) {
                        break;
                    }

                    Skill skill = skillController.getSkillByName(skillName.trim());

                    if (skill == null) {
                        return;
                    }
                    skills.add(skill);
                }

                generateTeamProposal(minTeamSize, maxTeamSize, skills);

            } catch (NumberFormatException e) {
                System.out.println("The first 2 values should be integers. Operation cancelled.");

            }
        }


    }

    /**
     * Generate team proposal.
     *
     * @param minTeamSize    the min team size
     * @param maxTeamSize    the max team size
     * @param requiredSkills the required skills
     */
    public void generateTeamProposal(int minTeamSize, int maxTeamSize, List<Skill> requiredSkills) {

        if (Utils.confirm("Do you want to generate the team proposals? (S/N)")) {
            try {
                List<Team> teamProposals = teamController.generateTeamProposals(minTeamSize, maxTeamSize, requiredSkills, collaboratorController.getCollaboratorList());
                System.out.println("Team proposals generated successfully.\n Number of possible teams: " + teamProposals.size());
                for (int i = 0; i < teamProposals.size(); i++) {
                    System.out.println("\nTeam Proposal " + (i + 1) + ":");
                    System.out.println(teamProposals.get(i).toString());
                    if (Utils.confirm("Do you accept this team? (S/N)")) {
                        System.out.println("The team proposal was successfully accepted.");
                        collaboratorController.startTask(teamProposals.get(i));
                        teamController.addTeam(teamProposals.get(i));
                        return;
                    }
                }
                System.out.println("There are no more team proposals. The operation ended with an error.");
            } catch (IllegalArgumentException e) {
                System.out.println("There was an error generating the team proposals.\n" + e.getMessage());
            }
        } else {
            System.out.println("Operation cancelled.");
        }
    }
}