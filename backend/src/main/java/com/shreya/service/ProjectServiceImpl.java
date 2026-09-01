package com.shreya.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shreya.exception.ChatException;
import com.shreya.exception.ProjectException;
import com.shreya.exception.UserException;
import com.shreya.model.Chat;
import com.shreya.model.Project;
import com.shreya.model.User;
import com.shreya.repository.ProjectRepository;

import jakarta.transaction.Transactional;

@Service
public class ProjectServiceImpl implements ProjectService {

	 @Autowired
	 private ProjectRepository projectRepository;

	 @Autowired
	 private ChatService chatService;
	 @Autowired
	 private InvitationService inviteTokenService;
	 
	 @Autowired 
	 private UserService userService;

	@Override
	public Project createProject(Project project,Long id) throws UserException  {
		User user = userService.findUserById(id);
		Project createdProject=new Project();

			createdProject.setOwner(user);
			createdProject.setTags(project.getTags());
			createdProject.setName(project.getName());
			createdProject.setCategory(project.getCategory());
			createdProject.setDescription(project.getDescription());
			createdProject.getTeam().add(user);

//			System.out.println(createdProject);
			Project savedProject=projectRepository.save(project);

			savedProject.getTeam().add(user);

			Chat chat = new Chat();
			chat.setProject(savedProject);
			Chat projectChat = chatService.createChat(chat);
			savedProject.setChat(projectChat);



		return savedProject;
	}

	@Override
	public List<Project> getProjectsByTeam(User user,String category,String tag) throws ProjectException {
		List<Project> projects= projectRepository.findByTeamContainingOrOwner(user,user);

		if (category != null) {
			projects = projects.stream()
					.filter(project -> project.getCategory().equals(category))
					.collect(Collectors.toList());
		}

		if (tag != null) {
			projects = projects.stream()
					.filter(project -> project.getTags().contains(tag))
					.collect(Collectors.toList());
		}

		return projects;
	}



	@Override
	public Project getProjectById(Long projectId) throws ProjectException {
		Optional<Project> project = projectRepository.findById(projectId);
		if(project.isPresent()) {
			return project.get();
		}
		throw new ProjectException("No project exists with the id "+projectId);
	}

	@Override
	public String deleteProject(Long projectId,Long id) throws UserException {
		User user = userService.findUserById(id);
		System.out.println("user ____>"+user);
		if(user!=null) {
			  projectRepository.deleteById(projectId);
			  return "project deleted";
	}
		throw new UserException("User doesnot exists");
	}

	@Override
	public Project updateProject(Project updatedProject, Long id) throws ProjectException {
		Project project = getProjectById(id);

		if (project == null) {
			throw new ProjectException("Project does not exist");
		}

		// Update the existing project with the fields from updatedProject
		if (updatedProject.getName() != null) {
			project.setName(updatedProject.getName());
		}

		if (updatedProject.getDescription() != null) {
			project.setDescription(updatedProject.getDescription());
		}

		if (updatedProject.getTags() != null) {
			project.setTags(updatedProject.getTags());
		}


		if (updatedProject.getCategory() != null) {
			project.setCategory(updatedProject.getCategory());
		}

		Project saved = projectRepository.save(project);
		return saved;
	}

	    @Override
	    public List<Project> searchProjects(String keyword, User user) throws ProjectException {
	        List<Project> list = projectRepository.findByNameContainingAndTeamContains(keyword,user);
	        if(list!=null) {
	        	return list;
	        }
	        throw new ProjectException("No Projects available");
	    }
	    
	    @Override
	    @Transactional
	    public void addUserToProject(Long projectId, Long userId) throws UserException, ProjectException {
	        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectException("project not found"));
	        User user = userService.findUserById(userId);

	        if (!project.getTeam().contains(user)) {
				project.getChat().getUsers().add(user);
				project.getTeam().add(user);
				projectRepository.save(project);
			}


	    }

	@Override
	public void removeUserFromProject(Long projectId, Long userId) throws UserException, ProjectException {
		Project project = projectRepository.findById(projectId)
				.orElseThrow(() -> new ProjectException("project not found"));
		User user = userService.findUserById(userId);

		if (project.getTeam().contains(user)) {
			project.getTeam().remove(user);
			project.getChat().getUsers().remove(user);
		}

	}

	@Override
	    public Chat getChatByProjectId(Long projectId) throws ProjectException, ChatException {
	        Project project = projectRepository.findById(projectId).orElseThrow(()-> new ProjectException("Project not found"));
	        if( project != null ) return project.getChat() ;
	        
	        
	        	throw new ChatException("no chats found");
	       
	    }


	
	    
	    
}
