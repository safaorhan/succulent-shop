package school.cactus.succulentshop.login.validation

import school.cactus.succulentshop.R
import school.cactus.succulentshop.validation.Validator

class PasswordValidator : Validator {
    override fun validate(field: String) = when {
        field.isEmpty() -> R.string.this_field_is_required
        else -> null
    }
}