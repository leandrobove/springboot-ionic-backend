package br.com.leandrobove.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.leandrobove.cursomc.dto.ClienteNewDTO;
import br.com.leandrobove.cursomc.entities.Cliente;
import br.com.leandrobove.cursomc.entities.enums.TipoCliente;
import br.com.leandrobove.cursomc.repositories.ClienteRepository;
import br.com.leandrobove.cursomc.resources.exceptions.FieldMessage;
import br.com.leandrobove.cursomc.services.validation.utils.CpfAndCnpjValidator;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();

		// VALIDAR SE É NULO
		if (objDto.getTipo() == null) {
			list.add(new FieldMessage("tipo", "O tipo não pode ser nulo."));
		}

		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getId())
				&& !CpfAndCnpjValidator.isValidCPF(objDto.getCpfOuCnpj())) {
			// CPF INVALIDO MESSAGE
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido."));
		}

		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getId())
				&& !CpfAndCnpjValidator.isValidCNPJ(objDto.getCpfOuCnpj())) {
			// CNPJ INVALIDO MESSAGE
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido."));
		}
		
		Cliente obj = repo.findByEmail(objDto.getEmail());
		
		if(obj != null) {
			list.add(new FieldMessage("email", "Este e-mail já está cadastrado"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}

		return list.isEmpty();
	}
}
