package ec.tec.ac.cr.juan_jop96.tarea6_firebase;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class producto_adapter extends BaseAdapter {
    private ArrayList<Producto> arrayItems;
    private Context context;
    private LayoutInflater layoutInflater;

    public producto_adapter(ArrayList<Producto> arrayItems, Context context) {
        this.arrayItems = arrayItems;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayItems.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vistaItem = layoutInflater.inflate(R.layout.lista_producto, parent, false);
        ImageView iv_imagen = vistaItem.findViewById(R.id.ls_imagen);
        TextView nombre = (TextView) vistaItem.findViewById(R.id.tx_nombre);
        TextView precio = (TextView) vistaItem.findViewById(R.id.tx_precio);
        TextView desc = (TextView) vistaItem.findViewById(R.id.tx_desc);
        ImageDownloadTask downloadTask = new ImageDownloadTask();
        try {
            Bitmap result = downloadTask.execute("https://firebasestorage.googleapis.com/v0/b/tarea-6-firebase.appspot.com/o/productos%2F"+arrayItems.get(i).getFoto()+"?alt=media").get();
            iv_imagen.setImageBitmap(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        nombre.setText("Nombre: "+arrayItems.get(i).getNombre());
        precio.setText("Precio: ₡"+arrayItems.get(i).getPrecio());
        desc.setText("Descripcion: "+arrayItems.get(i).getDescription());
        return vistaItem;
    }


}
