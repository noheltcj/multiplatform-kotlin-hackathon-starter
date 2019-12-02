package com.noheltcj.starter.adapter

import com.noheltcj.starter.adapter.localization.StringResource

interface LocalizationAdapter {
    operator fun get(stringResource: StringResource): String
}