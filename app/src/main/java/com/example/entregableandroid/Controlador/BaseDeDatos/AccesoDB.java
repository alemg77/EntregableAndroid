package com.example.entregableandroid.Controlador.BaseDeDatos;

public class AccesoDB {

    /*
    private ElementoListaDao elementoListaDao;
    private LiveData<List<ItemListaAPI>> todosLosItems;

    public AccesoDB(Application application){
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        elementoListaDao = appDatabase.elementoListaDao();
    }

    public void listaCompleta(){
        new listaCompletaTask(elementoListaDao).execute();
    }

    public void cantidadElementos(){
        new cantidadItemsTask(elementoListaDao).execute();
    }

    public void guardarElemento(ItemListaAPI itemListaAPI){
        new guargarElementoTask(elementoListaDao).execute(itemListaAPI);
    }

    private static class guargarElementoTask extends AsyncTask <ItemListaAPI, Void, Void> {
        private ElementoListaDao elementoListaDao;

        private guargarElementoTask(ElementoListaDao elementoListaDao){
            this.elementoListaDao = elementoListaDao;
        }

        @Override
        protected Void doInBackground(ItemListaAPI... itemListaAPIS) {
            elementoListaDao.insert(itemListaAPIS[0]);
            return null;
        }
    }

    private static class listaCompletaTask extends AsyncTask<Void, Void, LiveData<List<ItemListaAPI>>> {
        private ElementoListaDao elementoListaDao;

        private listaCompletaTask(ElementoListaDao elementoListaDao){
            this.elementoListaDao = elementoListaDao;
        }

        @Override
        protected LiveData<List<ItemListaAPI>> doInBackground(Void... voids) {
            LiveData<List<ItemListaAPI>> todos = elementoListaDao.getTodos();
            return todos;
        }
    }

    private static class cantidadItemsTask extends AsyncTask<Void, Void, LiveData<Integer>> {
        private ElementoListaDao elementoListaDao;

        private cantidadItemsTask(ElementoListaDao elementoListaDao){
            this.elementoListaDao = elementoListaDao;
        }

        @Override
        protected LiveData<Integer> doInBackground(Void... voids) {
            LiveData<Integer> integerLiveData = elementoListaDao.cantidadElementos();
            return integerLiveData;
        }
    }

     */

    /*
            try {
            long insert = db.elementoListaDao().insert(itemListaAPI);
            if (insert > 0) {
                Log.d(TAG, "Insercion en la base de datos correcto!!");
                if ( insert > 4 ) {
                    ItemListaAPI itemListaAPI1 = db.elementoListaDao().getPrimerElemento();
                    db.elementoListaDao().deleteById(itemListaAPI1.getId());
                }
            } else {
                Log.d(TAG, "Error en la insercion en la base de datos!!");
            }
        } catch ( Exception e ) {
            if ( e instanceof SQLiteConstraintException ) {
                Log.d(TAG, "No se agrego el elemento porque ya estaba");
            } else {
                Log.d(TAG, "Ocurrio la excepcion: " + e.toString());
            }
        }
     */

}
