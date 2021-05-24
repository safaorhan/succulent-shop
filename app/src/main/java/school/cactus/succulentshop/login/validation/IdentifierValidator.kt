package school.cactus.succulentshop.login.validation

import school.cactus.succulentshop.R
import school.cactus.succulentshop.validation.Validator


class IdentifierValidator : Validator {
    override fun validate(field: String?) = when {
        field.isNullOrEmpty() -> R.string.this_field_is_required
        field.length < 5 -> R.string.identifier_is_too_short
        else -> null
    }
}