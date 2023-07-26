package com.example.tss;

import com.example.tss.dto.ApplicantProfileDto;
import com.example.tss.dto.CircularDto;
import com.example.tss.entity.*;
import com.example.tss.exception.ApplicationPlacingFailedException;
import com.example.tss.repository.*;
import com.example.tss.service.ApplicationService;
import com.example.tss.service.BookMarkCircularService;
import com.example.tss.service.RoundService;
import com.example.tss.service.UserService;
import com.example.tss.service.impl.ApplicantProfileServiceImpl;
import com.example.tss.service.impl.CircularServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TraineeSelectionSystemApplicationTests {

	@Mock
	private ApplicantProfileServiceImpl applicantProfileService;

	@Mock
	private ResourceRepository resourceRepository;

	@Mock
	private CircularRepository circularRepository;

	@Mock
	private ApplicationService applicationService;

	@Mock
	private RoundService roundService;

	@Mock
	private ScreeningRoundMetaRepository screeningRoundMetaRepository;

	@Mock
	private ScreeningRoundRepository screeningRoundRepository;

	@Mock
	private BookMarkCircularService bookMarkCircularService;

	@Mock
	private UserService userService;

	@Mock
	private ApplicationRepository applicationRepository;

	@InjectMocks
	private CircularServiceImpl circularService;

	private ModelMapper modelMapper;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		modelMapper = new ModelMapper();
		circularService = new CircularServiceImpl(applicantProfileService, resourceRepository, circularRepository,
				modelMapper, applicationService, roundService, screeningRoundMetaRepository,
				screeningRoundRepository, bookMarkCircularService, userService, applicationRepository);
	}

	@Test
	void createCircularTest() {
		CircularDto circularDto = new CircularDto();
		// Set circularDto properties here

		Circular savedCircular = new Circular();
		// Set savedCircular properties here

		ScreeningRoundMeta savedScreeningRoundMeta = new ScreeningRoundMeta();
		// Set savedScreeningRoundMeta properties here

		when(circularRepository.save(any())).thenReturn(savedCircular);
		when(screeningRoundRepository.save(any())).thenReturn(new ScreeningRound());
		when(screeningRoundMetaRepository.save(any())).thenReturn(savedScreeningRoundMeta);

		ResponseEntity<?> responseEntity = circularService.createCircular(circularDto);
		assertNotNull(responseEntity);
		assertEquals(200, responseEntity.getStatusCodeValue());
		assertEquals(savedCircular, responseEntity.getBody());
	}

	@Test
	void applyTest() {
		Long circularId = 1L;
		ApplicantProfileDto applicantProfileDto = new ApplicantProfileDto();

		Principal principal = mock(Principal.class);
		User user = new User();
		// Set user properties here

		ApplicantProfile applicantProfile = new ApplicantProfile();
		// Set applicantProfile properties here

		Circular circular = new Circular();
		// Set circular properties here

		Application application = new Application();
		// Set application properties here

		when(userService.getUserByPrincipal(principal)).thenReturn(Optional.of(user));
		when(applicantProfileService.getByUser(user)).thenReturn(Optional.of(applicantProfile));
		when(circularRepository.findById(circularId)).thenReturn(Optional.of(circular));
		when(applicationRepository.findByCircularIdAndApplicantId(circularId, applicantProfile.getId()))
				.thenReturn(Optional.empty());
		when(resourceRepository.findByIdAndOwnerId(anyLong(), anyLong())).thenReturn(Optional.of(new Resource()));
		when(applicationRepository.save(any())).thenReturn(application);
		assertThrows(ApplicationPlacingFailedException.class,
				() -> circularService.apply(circularId, applicantProfileDto, principal));
	}
}
