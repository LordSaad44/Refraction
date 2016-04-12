package me.lordsaad.refraction.blocks;

import me.lordsaad.refraction.ModItems;
import me.lordsaad.refraction.Utils;
import me.lordsaad.refraction.network.PacketHandler;
import me.lordsaad.refraction.network.PacketMirror;
import me.lordsaad.refraction.tesrs.TESRMirror;
import me.lordsaad.refraction.tileentities.TileEntityMirror;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Saad on 3/24/2016.
 */
public class BlockMirror extends BlockDirectional implements ITileEntityProvider {

    public BlockMirror() {
        super(Material.glass);
        setHardness(0.75F);
        setUnlocalizedName("Mirror");
        setRegistryName("mirror");
        GameRegistry.registerBlock(this);
        GameRegistry.registerTileEntity(TileEntityMirror.class, "mirror");
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMirror.class, new TESRMirror());
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityMirror();
    }

    private TileEntityMirror getTE(World world, BlockPos pos) {
        return (TileEntityMirror) world.getTileEntity(pos);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            if (playerIn.inventory.getCurrentItem().getItem() == ModItems.screwdriver) {
                if (!playerIn.isSneaking()) {
                    TileEntityMirror mirror = getTE(worldIn, pos);

                    if (side != EnumFacing.UP && side != EnumFacing.DOWN) {
                        boolean top = hitY >= 0.7f, bottom = hitY <= 0.3;
                        boolean left = (side.getAxis() == EnumFacing.Axis.Z ? hitX : hitZ) >= 0.7f;
                        boolean right = (side.getAxis() == EnumFacing.Axis.Z ? hitX : hitZ) <= 0.3f;

                        if (side == EnumFacing.NORTH || side == EnumFacing.SOUTH) {

                            if (side == EnumFacing.SOUTH) {
                                if (top) mirror.subtractPitch(1);
                                else if (bottom) mirror.addPitch(1);
                            } else {
                                if (top) mirror.addPitch(1);
                                else if (bottom) mirror.subtractPitch(1);
                            }

                            if (left) mirror.subtractYaw(1);
                            else if (right) mirror.addYaw(1);

                        } else {

                            if (side == EnumFacing.EAST) {
                                if (top) mirror.addYaw(1);
                                else if (bottom) mirror.subtractYaw(1);
                            } else {
                                if (top) mirror.subtractYaw(1);
                                else if (bottom) mirror.addYaw(1);
                            }

                            if (left) mirror.addPitch(1);
                            else if (right) mirror.subtractPitch(1);
                        }

                        // SEND PACKETS //
                        PacketMirror packet = new PacketMirror(mirror.getYaw(), mirror.getPitch(), pos);
                        PacketHandler.INSTANCE.sendToAll(packet);

                        // RAYTRACE //
                        Vec3d centervec = new Vec3d(pos.getX() + 0.5, pos.getY() + 0.8, pos.getZ() + 0.5);

                        Vec3d lookvec = Utils.getVectorForRotation3d(mirror.getPitch(), mirror.getYaw()).normalize();
                        Vec3d startvec = centervec.add(lookvec);

                        Vec3d end = startvec.add(new Vec3d(lookvec.xCoord * 100, lookvec.yCoord * 100, lookvec.zCoord * 100));
                        RayTraceResult result = worldIn.rayTraceBlocks(startvec, end, false, false, true);
                        playerIn.addChatComponentMessage(new TextComponentString(TextFormatting.YELLOW + "pitch: " + mirror.getPitch() + " -- yaw:" + mirror.getYaw()));
                        playerIn.addChatComponentMessage(new TextComponentString(TextFormatting.YELLOW + worldIn.getBlockState(result.getBlockPos()).getBlock().getLocalizedName()));
                    }
                } else {
                    blockState.getBlock().dropBlockAsItem(worldIn, pos, state, 1);
                    worldIn.setBlockToAir(pos);
                }
            } else return false;
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntityMirror mirror = (TileEntityMirror) worldIn.getTileEntity(pos);
        EnumFacing facing = state.getValue(BlockMirror.FACING);
        if (facing == EnumFacing.NORTH) {
            mirror.setYaw(0);
            mirror.setPitch(-89);
        } else if (facing == EnumFacing.SOUTH) {
            mirror.setYaw(0);
            mirror.setPitch(89);
        } else if (facing == EnumFacing.EAST) {
            mirror.setYaw(-89);
            mirror.setPitch(0);
        } else if (facing == EnumFacing.WEST) {
            mirror.setYaw(89);
            mirror.setPitch(0);
        } else if (facing == EnumFacing.DOWN) {
            mirror.setYaw(179);
            mirror.setPitch(0);
        }
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getStateFromMeta(meta).withProperty(FACING, facing);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing;
        switch (meta & 7) {
            case 0:
                enumfacing = EnumFacing.DOWN;
                break;
            case 1:
                enumfacing = EnumFacing.EAST;
                break;
            case 2:
                enumfacing = EnumFacing.WEST;
                break;
            case 3:
                enumfacing = EnumFacing.SOUTH;
                break;
            case 4:
                enumfacing = EnumFacing.NORTH;
                break;
            case 5:
            default:
                enumfacing = EnumFacing.UP;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i;
        switch (state.getValue(FACING).ordinal()) {
            case 1:
                i = 1;
                break;
            case 2:
                i = 2;
                break;
            case 3:
                i = 3;
                break;
            case 4:
                i = 4;
                break;
            case 5:
            default:
                i = 5;
                break;
            case 6:
                i = 0;
        }
        return i;
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public boolean isVisuallyOpaque() {
        return false;
    }

    @Override
    public boolean isFullyOpaque(IBlockState state) {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isBlockNormalCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isBlockSolid(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

}