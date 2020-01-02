package com.duskitty.enderpillars.block;


import com.duskitty.enderpillars.block.entity.EnderTeleporterEntity;
import com.duskitty.enderpillars.container.Warp;
import com.duskitty.enderpillars.container.WarpStorage;
import com.duskitty.enderpillars.init.NetworkInit;
import com.duskitty.enderpillars.state.property.Properties;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Random;

public class EnderTeleporterBlock extends BlockWithEntity {

    private static final VoxelShape TOPSHAPE;
    private static final VoxelShape PILLAR;
    private static final VoxelShape BOTTOMSHAPE;
    public static final BooleanProperty HASPEARL;
    protected final Random random = new Random();

    public EnderTeleporterBlock() {
        super(FabricBlockSettings.of(Material.STONE).hardness(0.1F).sounds(BlockSoundGroup.STONE).lightLevel(4).build());
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(HASPEARL, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getMainHandStack().getItem() == Items.ENDER_PEARL && !state.get(HASPEARL)){
            player.getMainHandStack().decrement(1);
            world.setBlockState(pos, state.with(HASPEARL, true));
            if(!world.isClient()) {
                    System.out.println("SetSpawn!");
                    WarpStorage.fromPlayer((ServerPlayerEntity) player).setWarp(new Warp(pos.toString(), world.dimension.getType().toString(), player.getX(), player.getY(), player.getZ(), player.pitch, player.yaw));
            }
            player.setPlayerSpawn(player.getBlockPos(),true,false);

            for(int i = 0; i < 32; ++i) {
                world.addParticle(ParticleTypes.PORTAL, (double)pos.getX()+0.5, (double)pos.getY()+1, (double)pos.getZ()+0.5, this.random.nextGaussian(), 0.0D, this.random.nextGaussian());
            }
            return ActionResult.SUCCESS;
        }
        else if(state.get(HASPEARL) && player.isSneaking()){
            world.spawnEntity(new ItemEntity(world, (double)pos.getX()+0.5, (double)pos.getY()+1, (double)pos.getZ()+0.5, new ItemStack(Items.ENDER_PEARL)));
            world.setBlockState(pos, state.with(HASPEARL, false));
            return ActionResult.SUCCESS;
        }
        else if(state.get(HASPEARL)){
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof EnderTeleporterEntity) {
                if(!world.isClient()){
                    PacketByteBuf buffer = new PacketByteBuf(Unpooled.buffer());
                    WarpStorage.fromPlayer((ServerPlayerEntity) player).writeToBuf(buffer);
                    ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, NetworkInit.TELEPORTPACKET ,buffer);
                }
            }
            return ActionResult.SUCCESS;
        }
        else{
            return ActionResult.FAIL;
        }

    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos) {
        return BOTTOMSHAPE;
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if(state.get(HASPEARL) && !player.isCreative()){
            world.spawnEntity(new ItemEntity(world, (double)pos.getX()+0.5, (double)pos.getY()+1, (double)pos.getZ()+0.5, new ItemStack(Items.ENDER_PEARL)));
            world.playLevelEvent(player, 2001, pos, getRawIdFromState(state));
        }

        player.setPlayerSpawn(world.getSpawnPos(),true,true);
        world.playLevelEvent(player, 2001, pos, getRawIdFromState(state));
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return sideCoversSmallSquare(world, pos.down(), Direction.UP);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HASPEARL);
    }

    static {
        TOPSHAPE = Block.createCuboidShape(3D,13D,3D,13D,15D,13D);
        PILLAR = VoxelShapes.union(TOPSHAPE,Block.createCuboidShape(5D,2D,5D,11D,13D,11D));
        BOTTOMSHAPE = VoxelShapes.union(PILLAR,Block.createCuboidShape(3D,0D,3D,13D,2D,13D));
        HASPEARL = Properties.HASPEARL;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new EnderTeleporterEntity();
    }

    @Override
    public boolean isSimpleFullBlock(BlockState state, BlockView view, BlockPos pos) {
        return false;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }


}
