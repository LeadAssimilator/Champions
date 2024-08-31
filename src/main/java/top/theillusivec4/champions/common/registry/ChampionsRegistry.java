package top.theillusivec4.champions.common.registry;

import com.mojang.serialization.Codec;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import top.theillusivec4.champions.Champions;
import top.theillusivec4.champions.common.capability.ChampionAttachment;
import top.theillusivec4.champions.common.entity.ArcticBulletEntity;
import top.theillusivec4.champions.common.entity.EnkindlingBulletEntity;
import top.theillusivec4.champions.common.item.ChampionEggItem;
import top.theillusivec4.champions.common.loot.ChampionLootModifier;
import top.theillusivec4.champions.common.potion.ParalysisEffect;
import top.theillusivec4.champions.common.potion.WoundEffect;
import top.theillusivec4.champions.server.command.AffixArgumentInfo;
import top.theillusivec4.champions.server.command.AffixArgumentType;


public class ChampionsRegistry {

  public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
    DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Champions.MODID);
  private static final DeferredRegister<Item> EGG = DeferredRegister.create(BuiltInRegistries.ITEM, Champions.MODID);
  // RANK
  private static final DeferredRegister<ParticleType<?>> PARTICLE_TYPE = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Champions.MODID);
  // PARALYSIS
  private static final DeferredRegister<MobEffect> MOB_EFFECT = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Champions.MODID);
  private static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Champions.MODID);
  private static final DeferredRegister<ArgumentTypeInfo<?, ?>> ARGUMENT_TYPES = DeferredRegister.create(BuiltInRegistries.COMMAND_ARGUMENT_TYPE, Champions.MODID);
  private static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Champions.MODID);
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<ChampionAttachment.Provider>> CHAMPION_ATTACHMENT = ATTACHMENTS.register("champion_attachment", () -> AttachmentType.serializable(entity -> ChampionAttachment.createProvider((LivingEntity) entity)).build());
  public static DeferredHolder<Codec<? extends IGlobalLootModifier>, Codec<ChampionLootModifier>> CHAMPION_LOOT;
  public static DeferredHolder<EntityType<?>, EntityType<EnkindlingBulletEntity>> ENKINDLING_BULLET;
  public static DeferredHolder<EntityType<?>, EntityType<ArcticBulletEntity>> ARCTIC_BULLET;
  public static DeferredHolder<Item, ChampionEggItem> CHAMPION_EGG_ITEM;
  public static DeferredHolder<ParticleType<?>, SimpleParticleType> RANK_PARTICLE_TYPE;
  public static DeferredHolder<MobEffect, ParalysisEffect> PARALYSIS_EFFECT_TYPE;
  public static DeferredHolder<MobEffect, WoundEffect> WOUND_EFFECT_TYPE;
  public static DeferredHolder<ArgumentTypeInfo<?, ?>, AffixArgumentInfo> AFFIX_ARGUMENT_TYPE;

  public static void registerItems(IEventBus bus) {
    CHAMPION_EGG_ITEM = EGG.register("champion_egg", ChampionEggItem::new);
    EGG.register(bus);
  }

  public static void registerLootModifiers(IEventBus bus) {
    CHAMPION_LOOT = LOOT_MODIFIER_SERIALIZERS.register(
      "champion_loot", () -> ChampionLootModifier.CODEC);
    LOOT_MODIFIER_SERIALIZERS.register(bus);
  }

  public static void registerArgumentType(IEventBus bus) {
    AFFIX_ARGUMENT_TYPE = ARGUMENT_TYPES.register("affixes", () -> ArgumentTypeInfos.registerByClass(AffixArgumentType.class, new AffixArgumentInfo()));
    ARGUMENT_TYPES.register(bus);
  }

  public static void registerParticles(IEventBus bus) {
    RANK_PARTICLE_TYPE = PARTICLE_TYPE.register("rank", () -> new SimpleParticleType(true));
    PARTICLE_TYPE.register(bus);
  }

  public static void registerMobEffects(IEventBus bus) {
    PARALYSIS_EFFECT_TYPE = MOB_EFFECT.register("paralysis", ParalysisEffect::new);
    WOUND_EFFECT_TYPE = MOB_EFFECT.register("wound", WoundEffect::new);
    MOB_EFFECT.register(bus);
  }

  public static void registerEntityTypes(IEventBus bus) {
    ARCTIC_BULLET = ENTITY_TYPE.register("arctic_bullet", () -> EntityType.Builder.<ArcticBulletEntity>of(ArcticBulletEntity::new, MobCategory.MISC).sized(2, 2).build(Champions.MODID));
    ENKINDLING_BULLET = ENTITY_TYPE.register("enkindling_bullet", () -> EntityType.Builder.<EnkindlingBulletEntity>of(EnkindlingBulletEntity::new, MobCategory.MISC).sized(2, 2).build(Champions.MODID));
    ENTITY_TYPE.register(bus);
  }

  public static void register(IEventBus bus) {
    registerItems(bus);
    registerParticles(bus);
    registerMobEffects(bus);
    registerEntityTypes(bus);
    registerLootModifiers(bus);
    registerArgumentType(bus);
  }

}
