package com.siepert.createlegacy.tileentity;

import com.siepert.createapi.network.IKineticTE;
import com.siepert.createapi.network.KineticBlockInstance;
import com.siepert.createapi.network.NetworkContext;
import com.siepert.createlegacy.CreateLegacyConfigHolder;
import com.siepert.createlegacy.CreateLegacyModData;
import com.siepert.createlegacy.blocks.kinetic.BlockBlazeBurner;
import com.siepert.createlegacy.blocks.kinetic.BlockDrill;
import com.siepert.createlegacy.blocks.kinetic.BlockFan;
import com.siepert.createlegacy.mainRegistry.ModBlocks;
import com.siepert.createlegacy.util.handlers.recipes.WashingRecipes;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static com.siepert.createlegacy.blocks.kinetic.BlockDeployer.FACING;

public class TileEntityFan extends TileEntity implements IKineticTE {
    @Override
    public double getStressImpact() {
        return CreateLegacyConfigHolder.kineticConfig.encasedFanStressImpact;
    }

    @Override
    public double getStressCapacity() {
        return 0;
    }

    @Override
    public int getProducedSpeed() {
        return 0;
    }

    @Override
    public void kineticTick(NetworkContext context) {
        EnumFacing source = world.getBlockState(pos).getValue(BlockDrill.FACING).getOpposite();
        boolean isABlower = (!PROCESSORS.contains(world.getBlockState(pos.offset(source.getOpposite())).getBlock()) && !(world.getBlockState(pos.offset(source.getOpposite())).getBlock() instanceof BlockBlazeBurner));

        BlockPos posFront = pos.offset(source.getOpposite());

        double particleVX = source.getOpposite().getFrontOffsetX() * 2;
        double particleVY = source.getOpposite().getFrontOffsetY() * 2;
        double particleVZ = source.getOpposite().getFrontOffsetZ() * 2;

        if (isABlower) {
            if (!world.getBlockState(posFront).getMaterial().blocksMovement()
                    || isBlockNotObscure(world, pos.offset(source.getOpposite()),
                    world.getBlockState(pos.offset(source.getOpposite())))) {
                world.spawnParticle(EnumParticleTypes.CLOUD,
                        posFront.getX() + 0.5,
                        posFront.getY() + 0.5,
                        posFront.getZ() + 0.5,
                        particleVX,
                        particleVY,
                        particleVZ);
                for (int i = 0; i < CreateLegacyModData.random.nextInt(4) + 4; i++) {
                    world.spawnParticle(EnumParticleTypes.CLOUD,
                            posFront.getX() + CreateLegacyModData.random.nextFloat(),
                            posFront.getY() + CreateLegacyModData.random.nextFloat(),
                            posFront.getZ() + CreateLegacyModData.random.nextFloat(),
                            particleVX,
                            particleVY,
                            particleVZ);
                }


                AxisAlignedBB searchField = new AxisAlignedBB(pos.offset(source.getOpposite()));

                List<Entity> foundEntities = world.getEntitiesWithinAABB(Entity.class, searchField);

                for (Entity entity : foundEntities) {
                    double baseVX = entity.motionX;
                    double baseVY = entity.motionY;
                    double baseVZ = entity.motionZ;

                    switch (source.getOpposite()) {
                        case UP:
                            baseVY += 1.5;
                            entity.fallDistance = 0.0f;
                            break;
                        case DOWN:
                            baseVY -= 1.5;
                            break;
                        case NORTH:
                            baseVZ -= 1.5;
                            break;
                        case EAST:
                            baseVX += 1.5;
                            break;
                        case SOUTH:
                            baseVZ += 1.5;
                            break;
                        case WEST:
                            baseVX -= 1.5;
                            break;
                    }

                    entity.setVelocity(baseVX, baseVY, baseVZ);
                }
            } else return;

            if (!world.getBlockState(pos.offset(source.getOpposite(), 2)).getMaterial().blocksMovement()
                    || isBlockNotObscure(world, pos.offset(source.getOpposite(), 2),
                    world.getBlockState(pos.offset(source.getOpposite(), 2)))) {
                AxisAlignedBB searchField = new AxisAlignedBB(pos.offset(source.getOpposite(), 2));

                List<Entity> foundEntities = world.getEntitiesWithinAABB(Entity.class, searchField);

                for (Entity entity : foundEntities) {
                    double baseVX = entity.motionX;
                    double baseVY = entity.motionY;
                    double baseVZ = entity.motionZ;

                    switch (source.getOpposite()) {
                        case UP:
                            baseVY += 1;
                            entity.fallDistance = 0.0f;
                            break;
                        case DOWN:
                            baseVY -= 1;
                            break;
                        case NORTH:
                            baseVZ -= 1;
                            break;
                        case EAST:
                            baseVX += 1;
                            break;
                        case SOUTH:
                            baseVZ += 1;
                            break;
                        case WEST:
                            baseVX -= 1;
                            break;
                    }

                    entity.setVelocity(baseVX, baseVY, baseVZ);
                }
            } else return;

            if (!world.getBlockState(pos.offset(source.getOpposite(), 3)).getMaterial().blocksMovement()
                    || isBlockNotObscure(world, pos.offset(source.getOpposite(), 3),
                    world.getBlockState(pos.offset(source.getOpposite(), 3)))) {
                AxisAlignedBB searchField = new AxisAlignedBB(pos.offset(source.getOpposite(), 3));

                List<Entity> foundEntities = world.getEntitiesWithinAABB(Entity.class, searchField);

                for (Entity entity : foundEntities) {
                    double baseVX = entity.motionX;
                    double baseVY = entity.motionY;
                    double baseVZ = entity.motionZ;

                    switch (source.getOpposite()) {
                        case UP:
                            baseVY += 0.5;
                            entity.fallDistance = 0.0f;
                            break;
                        case DOWN:
                            baseVY -= 0.5;
                            break;
                        case NORTH:
                            baseVZ -= 0.5;
                            break;
                        case EAST:
                            baseVX += 0.5;
                            break;
                        case SOUTH:
                            baseVZ += 0.5;
                            break;
                        case WEST:
                            baseVX -= 0.5;
                            break;
                    }

                    entity.setVelocity(baseVX, baseVY, baseVZ);
                }
            } else return;
        }

        ProcessingType whatsTheProcess = ProcessingType.getType(world.getBlockState(posFront));

        if (whatsTheProcess != null) {
            for (int i = 0; i < 5; i++) {
                BlockPos startProcessPos = pos.offset(source.getOpposite(), 1 + i);

                if (isBlockNotObscure(world, startProcessPos, world.getBlockState(startProcessPos))
                        || !world.getBlockState(startProcessPos).getMaterial().blocksMovement()) {

                    if (whatsTheProcess == ProcessingType.WASH) {

                        world.spawnParticle(EnumParticleTypes.CLOUD,
                                posFront.getX() + 0.5,
                                posFront.getY() + 0.5,
                                posFront.getZ() + 0.5,
                                particleVX / 3,
                                particleVY / 3,
                                particleVZ / 3);
                        for (int j = 0; j < CreateLegacyModData.random.nextInt(4) + 4; j++) {
                            world.spawnParticle(EnumParticleTypes.CLOUD,
                                    posFront.getX() + CreateLegacyModData.random.nextFloat(),
                                    posFront.getY() + CreateLegacyModData.random.nextFloat(),
                                    posFront.getZ() + CreateLegacyModData.random.nextFloat(),
                                    particleVX / 3,
                                    particleVY / 3,
                                    particleVZ / 3);
                        }
                        for (int j = 0; j < CreateLegacyModData.random.nextInt(4); j++) {
                            world.spawnParticle(EnumParticleTypes.WATER_BUBBLE,
                                    posFront.getX() + CreateLegacyModData.random.nextFloat(),
                                    posFront.getY() + CreateLegacyModData.random.nextFloat(),
                                    posFront.getZ() + CreateLegacyModData.random.nextFloat(),
                                    particleVX / 3,
                                    particleVY / 3,
                                    particleVZ / 3);
                        }
                    }
                    if (whatsTheProcess == ProcessingType.SMELT) {
                        world.spawnParticle(EnumParticleTypes.SMOKE_LARGE,
                                posFront.getX() + 0.5,
                                posFront.getY() + 0.5,
                                posFront.getZ() + 0.5,
                                particleVX / 3,
                                particleVY / 3,
                                particleVZ / 3);
                        for (int j = 0; j < CreateLegacyModData.random.nextInt(4) + 4; j++) {
                            world.spawnParticle(EnumParticleTypes.SMOKE_LARGE,
                                    posFront.getX() + CreateLegacyModData.random.nextFloat(),
                                    posFront.getY() + CreateLegacyModData.random.nextFloat(),
                                    posFront.getZ() + CreateLegacyModData.random.nextFloat(),
                                    particleVX / 3,
                                    particleVY / 3,
                                    particleVZ / 3);
                        }

                    }

                    AxisAlignedBB itemSearchArea = new AxisAlignedBB(startProcessPos.offset(source.getOpposite()));
                    List<EntityItem> foundItems = world.getEntitiesWithinAABB(EntityItem.class, itemSearchArea);

                    List<Entity> foundEntities = world.getEntitiesWithinAABB(Entity.class, itemSearchArea);

                    if (whatsTheProcess == ProcessingType.SMELT) {
                        for (Entity entity : foundEntities) {
                            if (!(entity instanceof EntityItem)) {
                                entity.setFire(5);
                            }
                        }
                    }
                    if (whatsTheProcess == ProcessingType.WASH) {
                        for (Entity entity : foundEntities) {
                            if (!(entity instanceof EntityItem)) {
                                entity.extinguish();
                            }
                        }
                    }

                    for (EntityItem entityItem : foundItems) {
                        if (world.isRemote) {
                            if (whatsTheProcess == ProcessingType.WASH) {
                                for (int k = 0; k < CreateLegacyModData.random.nextInt(10); k++) {
                                    world.spawnParticle(EnumParticleTypes.WATER_SPLASH,
                                            entityItem.posX, entityItem.posY + 0.2, entityItem.posZ,
                                            0, CreateLegacyModData.random.nextFloat(), 0);
                                }
                            }
                            if (whatsTheProcess == ProcessingType.SMELT) {
                                world.spawnParticle(EnumParticleTypes.FLAME,
                                        entityItem.posX, entityItem.posY + 0.2, entityItem.posZ,
                                        0, 0, 0);

                                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
                                        entityItem.posX, entityItem.posY + 0.2, entityItem.posZ,
                                        0, CreateLegacyModData.random.nextFloat() / 2, 0);
                                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
                                        entityItem.posX, entityItem.posY + 0.2, entityItem.posZ,
                                        0, CreateLegacyModData.random.nextFloat() / 2, 0);
                                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
                                        entityItem.posX, entityItem.posY + 0.2, entityItem.posZ,
                                        0, CreateLegacyModData.random.nextFloat() / 2, 0);
                            }


                        }
                        if (!world.isRemote) {
                            if (whatsTheProcess == ProcessingType.SMELT) {
                                SmeltResultSet resultSet = applicateSmelt(entityItem.getItem(), whatsTheProcess);
                                if (resultSet.hasRecipe) {
                                    entityItem.getItem().shrink(1);
                                    EntityItem resultEntityItem = new EntityItem(world, entityItem.posX, entityItem.posY, entityItem.posZ,
                                            resultSet.stack);
                                    resultEntityItem.setVelocity(0, 0, 0);
                                    world.spawnEntity(resultEntityItem);

                                    world.playSound(null, entityItem.posX, entityItem.posY, entityItem.posZ,
                                            SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.AMBIENT, 0.25f, 1.0f);

                                    if (entityItem.getItem().getCount() == 0 || entityItem.getItem().isEmpty()) {
                                        entityItem.setDead();
                                    }
                                }
                            }
                            if (whatsTheProcess == ProcessingType.WASH) {
                                WashResultSet resultSet = applicateWash(entityItem.getItem(), whatsTheProcess);
                                assert resultSet != null;
                                if (resultSet.hasRecipe) {
                                    entityItem.getItem().shrink(1);
                                    EntityItem resultEntityItem = new EntityItem(world, entityItem.posX, entityItem.posY, entityItem.posZ,
                                            resultSet.stack);
                                    resultEntityItem.setVelocity(0, 0, 0);
                                    world.spawnEntity(resultEntityItem);

                                    if (resultSet.hasOptional()) {
                                        EntityItem resultEntityItemOptional = new EntityItem(world, entityItem.posX, entityItem.posY, entityItem.posZ,
                                                resultSet.stackOptional);
                                        resultEntityItemOptional.setVelocity(0, 0, 0);
                                        world.spawnEntity(resultEntityItemOptional);
                                    }

                                    world.playSound(null, entityItem.posX, entityItem.posY, entityItem.posZ,
                                            SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.AMBIENT, 0.25f, 1.0f);

                                    if (entityItem.getItem().getCount() == 0 || entityItem.getItem().isEmpty()) {
                                        entityItem.setDead();
                                    }
                                }
                            }
                        }
                    }
                } else return;
            }
        }
    }

    @Override
    public void setUpdated() {

    }

    @Override
    public boolean isConsumer() {
        return true;
    }

    private enum ProcessingType {
        WASH, SMELT;

        public static @Nullable ProcessingType getType(IBlockState blockstateIn) {
            if (blockstateIn.getBlock().equals(Blocks.WATER) || blockstateIn.getBlock().equals(Blocks.FLOWING_WATER)) {
                return WASH;
            }
            if (blockstateIn.getBlock().equals(Blocks.LAVA) || blockstateIn.getBlock().equals(Blocks.FLOWING_LAVA)
                    || blockstateIn.getBlock().equals(Blocks.FIRE)) {
                return SMELT;
            }
            if (blockstateIn.getBlock() instanceof BlockBlazeBurner) {
                if (blockstateIn.getValue(BlockBlazeBurner.STATE).getMeta() > 1) return SMELT;
            }
            return null;
        }
    }

    private static final List<Block> PROCESSORS = new ArrayList<>();

    static {
        PROCESSORS.add(Blocks.WATER);
        PROCESSORS.add(Blocks.FLOWING_WATER);
        PROCESSORS.add(Blocks.LAVA);
        PROCESSORS.add(Blocks.FLOWING_LAVA);
        PROCESSORS.add(Blocks.FIRE);
    }

    private boolean isBlockNotObscure(IBlockAccess access, BlockPos pos, IBlockState stateOfOhio) {
        List<Block> predef_allowances = new ArrayList<Block>();
        predef_allowances.add(Blocks.STANDING_SIGN);
        predef_allowances.add(Blocks.WALL_SIGN);
        predef_allowances.add(Blocks.LADDER);
        predef_allowances.add(Blocks.IRON_BARS);
        predef_allowances.add(ModBlocks.BLAZE_BURNER);

        if (predef_allowances.contains(stateOfOhio.getBlock()))return true;

        if (!stateOfOhio.getMaterial().blocksMovement()) return true;
        if (stateOfOhio.getCollisionBoundingBox(access, pos) == Block.NULL_AABB) return true;

        return false;
    }

    public @Nullable SmeltResultSet applicateSmelt(@Nonnull ItemStack stack, TileEntityFan.ProcessingType type) {
        if (stack.isEmpty()) {
            return new SmeltResultSet(stack, false);
        }
        else {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(stack);

            if (itemstack.isEmpty()) {
                return new SmeltResultSet(stack, false);
            } else {
                ItemStack itemstack1 = itemstack.copy();
                itemstack1.setCount(itemstack.getCount());
                return new SmeltResultSet(itemstack1, true);
            }
        }
    }
    public @Nullable WashResultSet applicateWash(@Nonnull ItemStack stack, TileEntityFan.ProcessingType type) {
        if (stack.isEmpty()) {
            return new WashResultSet(stack, false);
        }
        else {
            ItemStack itemstack = WashingRecipes.instance().getWashingResult(stack);
            ItemStack stackOpt = WashingRecipes.instance().getOptionalResult(stack);

            if (itemstack.isEmpty() && stackOpt.isEmpty()) {
                return new WashResultSet(stack, false);
            } else {
                ItemStack itemstack1 = itemstack.copy();
                itemstack1.setCount(itemstack.getCount());
                ItemStack itemstack2 = stackOpt.copy();
                itemstack2.setCount(stackOpt.getCount());

                if (CreateLegacyModData.random.nextInt(5) == 0) {
                    return new WashResultSet(itemstack1, itemstack2, true);
                }
                return new WashResultSet(itemstack1, true);
            }
        }
    }

    private class SmeltResultSet {
        ItemStack stack;
        boolean hasRecipe;
        private SmeltResultSet(ItemStack stack, boolean hasRecipe) {
            this.stack = stack;
            this.hasRecipe = hasRecipe;
        }

        public boolean hasRecipe() {
            return hasRecipe;
        }

        public ItemStack getResult() {
            return stack;
        }
    }
    private class WashResultSet {
        ItemStack stack, stackOptional;
        boolean hasRecipe, hasOptional;

        private WashResultSet(ItemStack stack, boolean hasRecipe) {
            this.stack = stack;
            this.stackOptional = ItemStack.EMPTY;
            this.hasOptional = false;
            this.hasRecipe = hasRecipe;
        }

        private WashResultSet(ItemStack stack, ItemStack stackOptional, boolean hasRecipe) {
            this.stack = stack;
            this.stackOptional = stackOptional;
            this.hasOptional = true;
            this.hasRecipe = hasRecipe;
        }

        public boolean hasRecipe() {
            return hasRecipe;
        }

        public ItemStack getResult() {
            return stack;
        }
        public ItemStack getResultOptional() {
            return stackOptional;
        }


        public boolean hasOptional() {
            return hasOptional;
        }
    }

    @Override
    public void passNetwork(NetworkContext context, EnumFacing source, boolean srcIsCog, boolean srcCogIsHorizontal, boolean inverted) {
        if (srcIsCog) return; //We don't accept a cog as input, we need a shaft!

        IBlockState myState = world.getBlockState(pos);


        if (source == myState.getValue(BlockFan.FACING).getOpposite()) {
            //iteratedBlocks.add(pos);
            context.addKineticBlockInstance(new KineticBlockInstance(pos, inverted));
        }
    }

    @Deprecated
    public void passRotation(World worldIn, BlockPos pos, EnumFacing source, List<BlockPos> iteratedBlocks,
                             boolean srcIsCog, boolean srcCogIsHorizontal, boolean inverseRotation) {
        if (srcIsCog) return; //We don't accept a cog as input, we need a shaft!

        IBlockState myState = worldIn.getBlockState(pos);


        if (source == myState.getValue(BlockFan.FACING).getOpposite()) {
            iteratedBlocks.add(pos);

            boolean isABlower = (!PROCESSORS.contains(worldIn.getBlockState(pos.offset(source.getOpposite())).getBlock()) && !(worldIn.getBlockState(pos.offset(source.getOpposite())).getBlock() instanceof BlockBlazeBurner));

            BlockPos posFront = pos.offset(source.getOpposite());

            double particleVX = source.getOpposite().getFrontOffsetX() * 2;
            double particleVY = source.getOpposite().getFrontOffsetY() * 2;
            double particleVZ = source.getOpposite().getFrontOffsetZ() * 2;

            if (isABlower) {
                if (!worldIn.getBlockState(posFront).getMaterial().blocksMovement()
                        || isBlockNotObscure(worldIn, pos.offset(source.getOpposite()),
                        worldIn.getBlockState(pos.offset(source.getOpposite())))) {
                    worldIn.spawnParticle(EnumParticleTypes.CLOUD,
                            posFront.getX() + 0.5,
                            posFront.getY() + 0.5,
                            posFront.getZ() + 0.5,
                            particleVX,
                            particleVY,
                            particleVZ);
                    for (int i = 0; i < CreateLegacyModData.random.nextInt(4) + 4; i++) {
                        worldIn.spawnParticle(EnumParticleTypes.CLOUD,
                                posFront.getX() + CreateLegacyModData.random.nextFloat(),
                                posFront.getY() + CreateLegacyModData.random.nextFloat(),
                                posFront.getZ() + CreateLegacyModData.random.nextFloat(),
                                particleVX,
                                particleVY,
                                particleVZ);
                    }


                    AxisAlignedBB searchField = new AxisAlignedBB(pos.offset(source.getOpposite()));

                    List<Entity> foundEntities = worldIn.getEntitiesWithinAABB(Entity.class, searchField);

                    for (Entity entity : foundEntities) {
                        double baseVX = entity.motionX;
                        double baseVY = entity.motionY;
                        double baseVZ = entity.motionZ;

                        switch (source.getOpposite()) {
                            case UP:
                                baseVY += 1.5;
                                entity.fallDistance = 0.0f;
                                break;
                            case DOWN:
                                baseVY -= 1.5;
                                break;
                            case NORTH:
                                baseVZ -= 1.5;
                                break;
                            case EAST:
                                baseVX += 1.5;
                                break;
                            case SOUTH:
                                baseVZ += 1.5;
                                break;
                            case WEST:
                                baseVX -= 1.5;
                                break;
                        }

                        entity.setVelocity(baseVX, baseVY, baseVZ);
                    }
                } else return;

                if (!worldIn.getBlockState(pos.offset(source.getOpposite(), 2)).getMaterial().blocksMovement()
                        || isBlockNotObscure(worldIn, pos.offset(source.getOpposite(), 2),
                        worldIn.getBlockState(pos.offset(source.getOpposite(), 2)))) {
                    AxisAlignedBB searchField = new AxisAlignedBB(pos.offset(source.getOpposite(), 2));

                    List<Entity> foundEntities = worldIn.getEntitiesWithinAABB(Entity.class, searchField);

                    for (Entity entity : foundEntities) {
                        double baseVX = entity.motionX;
                        double baseVY = entity.motionY;
                        double baseVZ = entity.motionZ;

                        switch (source.getOpposite()) {
                            case UP:
                                baseVY += 1;
                                entity.fallDistance = 0.0f;
                                break;
                            case DOWN:
                                baseVY -= 1;
                                break;
                            case NORTH:
                                baseVZ -= 1;
                                break;
                            case EAST:
                                baseVX += 1;
                                break;
                            case SOUTH:
                                baseVZ += 1;
                                break;
                            case WEST:
                                baseVX -= 1;
                                break;
                        }

                        entity.setVelocity(baseVX, baseVY, baseVZ);
                    }
                } else return;

                if (!worldIn.getBlockState(pos.offset(source.getOpposite(), 3)).getMaterial().blocksMovement()
                        || isBlockNotObscure(worldIn, pos.offset(source.getOpposite(), 3),
                        worldIn.getBlockState(pos.offset(source.getOpposite(), 3)))) {
                    AxisAlignedBB searchField = new AxisAlignedBB(pos.offset(source.getOpposite(), 3));

                    List<Entity> foundEntities = worldIn.getEntitiesWithinAABB(Entity.class, searchField);

                    for (Entity entity : foundEntities) {
                        double baseVX = entity.motionX;
                        double baseVY = entity.motionY;
                        double baseVZ = entity.motionZ;

                        switch (source.getOpposite()) {
                            case UP:
                                baseVY += 0.5;
                                entity.fallDistance = 0.0f;
                                break;
                            case DOWN:
                                baseVY -= 0.5;
                                break;
                            case NORTH:
                                baseVZ -= 0.5;
                                break;
                            case EAST:
                                baseVX += 0.5;
                                break;
                            case SOUTH:
                                baseVZ += 0.5;
                                break;
                            case WEST:
                                baseVX -= 0.5;
                                break;
                        }

                        entity.setVelocity(baseVX, baseVY, baseVZ);
                    }
                } else return;
            }

            TileEntityFan.ProcessingType whatsTheProcess = TileEntityFan.ProcessingType.getType(worldIn.getBlockState(posFront));

            if (whatsTheProcess != null) {
                for (int i = 0; i < 5; i++) {
                    BlockPos startProcessPos = pos.offset(source.getOpposite(), 1 + i);

                    if (isBlockNotObscure(worldIn, startProcessPos, worldIn.getBlockState(startProcessPos))
                            || !worldIn.getBlockState(startProcessPos).getMaterial().blocksMovement()) {

                        if (whatsTheProcess == TileEntityFan.ProcessingType.WASH) {

                            worldIn.spawnParticle(EnumParticleTypes.CLOUD,
                                    posFront.getX() + 0.5,
                                    posFront.getY() + 0.5,
                                    posFront.getZ() + 0.5,
                                    particleVX / 3,
                                    particleVY / 3,
                                    particleVZ / 3);
                            for (int j = 0; j < CreateLegacyModData.random.nextInt(4) + 4; j++) {
                                worldIn.spawnParticle(EnumParticleTypes.CLOUD,
                                        posFront.getX() + CreateLegacyModData.random.nextFloat(),
                                        posFront.getY() + CreateLegacyModData.random.nextFloat(),
                                        posFront.getZ() + CreateLegacyModData.random.nextFloat(),
                                        particleVX / 3,
                                        particleVY / 3,
                                        particleVZ / 3);
                            }
                            for (int j = 0; j < CreateLegacyModData.random.nextInt(4); j++) {
                                worldIn.spawnParticle(EnumParticleTypes.WATER_BUBBLE,
                                        posFront.getX() + CreateLegacyModData.random.nextFloat(),
                                        posFront.getY() + CreateLegacyModData.random.nextFloat(),
                                        posFront.getZ() + CreateLegacyModData.random.nextFloat(),
                                        particleVX / 3,
                                        particleVY / 3,
                                        particleVZ / 3);
                            }
                        }
                        if (whatsTheProcess == TileEntityFan.ProcessingType.SMELT) {
                            worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE,
                                    posFront.getX() + 0.5,
                                    posFront.getY() + 0.5,
                                    posFront.getZ() + 0.5,
                                    particleVX / 3,
                                    particleVY / 3,
                                    particleVZ / 3);
                            for (int j = 0; j < CreateLegacyModData.random.nextInt(4) + 4; j++) {
                                worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE,
                                        posFront.getX() + CreateLegacyModData.random.nextFloat(),
                                        posFront.getY() + CreateLegacyModData.random.nextFloat(),
                                        posFront.getZ() + CreateLegacyModData.random.nextFloat(),
                                        particleVX / 3,
                                        particleVY / 3,
                                        particleVZ / 3);
                            }

                        }

                        AxisAlignedBB itemSearchArea = new AxisAlignedBB(startProcessPos.offset(source.getOpposite()));
                        List<EntityItem> foundItems = worldIn.getEntitiesWithinAABB(EntityItem.class, itemSearchArea);

                        List<Entity> foundEntities = worldIn.getEntitiesWithinAABB(Entity.class, itemSearchArea);

                        if (whatsTheProcess == TileEntityFan.ProcessingType.SMELT) {
                            for (Entity entity : foundEntities) {
                                if (!(entity instanceof EntityItem)) {
                                    entity.setFire(5);
                                }
                            }
                        }
                        if (whatsTheProcess == TileEntityFan.ProcessingType.WASH) {
                            for (Entity entity : foundEntities) {
                                if (!(entity instanceof EntityItem)) {
                                    entity.extinguish();
                                }
                            }
                        }

                        for (EntityItem entityItem : foundItems) {
                            if (worldIn.isRemote) {
                                if (whatsTheProcess == TileEntityFan.ProcessingType.WASH) {
                                    for (int k = 0; k < CreateLegacyModData.random.nextInt(10); k++) {
                                        worldIn.spawnParticle(EnumParticleTypes.WATER_SPLASH,
                                                entityItem.posX, entityItem.posY + 0.2, entityItem.posZ,
                                                0, CreateLegacyModData.random.nextFloat(), 0);
                                    }
                                }
                                if (whatsTheProcess == TileEntityFan.ProcessingType.SMELT) {
                                    worldIn.spawnParticle(EnumParticleTypes.FLAME,
                                            entityItem.posX, entityItem.posY + 0.2, entityItem.posZ,
                                            0, 0, 0);

                                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
                                            entityItem.posX, entityItem.posY + 0.2, entityItem.posZ,
                                            0, CreateLegacyModData.random.nextFloat() / 2, 0);
                                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
                                            entityItem.posX, entityItem.posY + 0.2, entityItem.posZ,
                                            0, CreateLegacyModData.random.nextFloat() / 2, 0);
                                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
                                            entityItem.posX, entityItem.posY + 0.2, entityItem.posZ,
                                            0, CreateLegacyModData.random.nextFloat() / 2, 0);
                                }


                            }
                            if (!worldIn.isRemote) {
                                if (whatsTheProcess == TileEntityFan.ProcessingType.SMELT) {
                                    TileEntityFan.SmeltResultSet resultSet = applicateSmelt(entityItem.getItem(), whatsTheProcess);
                                    if (resultSet.hasRecipe) {
                                        entityItem.getItem().shrink(1);
                                        EntityItem resultEntityItem = new EntityItem(worldIn, entityItem.posX, entityItem.posY, entityItem.posZ,
                                                resultSet.stack);
                                        resultEntityItem.setVelocity(0, 0, 0);
                                        worldIn.spawnEntity(resultEntityItem);

                                        worldIn.playSound(null, entityItem.posX, entityItem.posY, entityItem.posZ,
                                                SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.AMBIENT, 0.25f, 1.0f);

                                        if (entityItem.getItem().getCount() == 0 || entityItem.getItem().isEmpty()) {
                                            entityItem.setDead();
                                        }
                                    }
                                }
                                if (whatsTheProcess == TileEntityFan.ProcessingType.WASH) {
                                    TileEntityFan.WashResultSet resultSet = applicateWash(entityItem.getItem(), whatsTheProcess);
                                    assert resultSet != null;
                                    if (resultSet.hasRecipe) {
                                        entityItem.getItem().shrink(1);
                                        EntityItem resultEntityItem = new EntityItem(worldIn, entityItem.posX, entityItem.posY, entityItem.posZ,
                                                resultSet.stack);
                                        resultEntityItem.setVelocity(0, 0, 0);
                                        worldIn.spawnEntity(resultEntityItem);

                                        if (resultSet.hasOptional()) {
                                            EntityItem resultEntityItemOptional = new EntityItem(worldIn, entityItem.posX, entityItem.posY, entityItem.posZ,
                                                    resultSet.stackOptional);
                                            resultEntityItemOptional.setVelocity(0, 0, 0);
                                            worldIn.spawnEntity(resultEntityItemOptional);
                                        }

                                        worldIn.playSound(null, entityItem.posX, entityItem.posY, entityItem.posZ,
                                                SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.AMBIENT, 0.25f, 1.0f);

                                        if (entityItem.getItem().getCount() == 0 || entityItem.getItem().isEmpty()) {
                                            entityItem.setDead();
                                        }
                                    }
                                }
                            }
                        }
                    } else return;
                }
            }
        }
    }

}
