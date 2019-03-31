package com.noheltcj.starter.android.adapter

import android.content.Context
import com.noheltcj.starter.android.adapter.mapper.StringResourceMapper
import com.noheltcj.starter.adapter.LocalizationAdapter
import com.noheltcj.starter.adapter.localization.StringResource
import javax.inject.Inject

class LocalizationAdapterImpl @Inject constructor(
    private val context: Context,
    private val stringResourceMapper: StringResourceMapper
) : LocalizationAdapter {
    override fun get(stringResource: StringResource): String =
        context.getString(stringResourceMapper.map(stringResource))
}