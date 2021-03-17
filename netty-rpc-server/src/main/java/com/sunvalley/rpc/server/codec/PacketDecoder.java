package com.sunvalley.rpc.server.codec;

import com.sunvalley.rpc.server.utils.ObjectUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/15 11:22
 */

public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        in.markReaderIndex();

        if (in.readableBytes() < 4) {
            System.out.println("readableBytes length less than 4 bytes");
            in.resetReaderIndex();
            return;
        }

        int length = in.readInt();

        if (length < 0) {
            ctx.close();
            System.out.println("message length less than 0, channel closed");
            return;
        }

        ByteBuf byteBuf = Unpooled.buffer(length);

        in.readBytes(byteBuf);

        try {
            byte[] body = byteBuf.array();
            Object message = ObjectUtils.bytesToObject(body);
            out.add(message);
        } catch (Exception e) {
            System.out.println(ctx.channel().remoteAddress() + ",decode failed." + e.getMessage());
        }
    }
}
