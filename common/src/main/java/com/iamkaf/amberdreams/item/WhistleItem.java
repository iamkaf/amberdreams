package com.iamkaf.amberdreams.item;

import com.iamkaf.amberdreams.tags.Tags;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class WhistleItem extends InstrumentItem {
    public WhistleItem(TagKey<Instrument> tag) {
        super(new Properties().stacksTo(1), tag);
    }
}
